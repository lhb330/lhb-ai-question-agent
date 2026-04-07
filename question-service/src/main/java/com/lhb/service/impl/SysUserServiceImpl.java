package com.lhb.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.constants.RedisKeyConst;
import com.lhb.constants.UserConst;
import com.lhb.dto.LoginDTO;
import com.lhb.dto.UserQueryDTO;
import com.lhb.entity.*;
import com.lhb.enums.DeviceTypeEnum;
import com.lhb.enums.DictTypeEnum;
import com.lhb.enums.StatusEnum;
import com.lhb.mapper.SysUserMapper;
import com.lhb.service.*;
import com.lhb.utils.RedisUtil;
import com.lhb.utils.ThrowUtils;
import com.lhb.vo.AuthUserVO;
import com.lhb.vo.SysUserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Value("${sa-token.timeout}")
    private long tokenTimeout;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SchoolInfoService schoolInfoService;

    @Resource
    private GradeInfoService gradeInfoService;

    @Resource
    private ClassesInfoService classesInfoService;

    @Resource
    private SysDictService sysDictService;

    @Resource
    private SysLoginLogService sysLoginLogService;

    /**
     * 用户登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AuthUserVO userLogin(LoginDTO dto) {
        Assert.notBlank(dto.getUserAccount(), "登录账号不能为空");
        Assert.notBlank(dto.getUserPassword(), "登录密码不能为空");

        // 获取请求信息
        HttpServletRequest request = getCurrentRequest();
        String clientIp = getClientIP(request);
        String deviceType = getDeviceType(request);

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserAccount, dto.getUserAccount());
        queryWrapper.eq(SysUser::getUserPassword, getEncryptPassword(dto.getUserPassword()));

        SysUser user = this.getOne(queryWrapper,false);

        // 用户不存在直接返回
        if (ObjUtil.isNull(user)) {
            log.warn("登录失败:账号不存在或密码错误 | IP:{} | 账号:{}", clientIp, dto.getUserAccount());
            // 记录失败日志（异步）
            sysLoginLogService.saveLoginLog(dto.getUserAccount(), null, deviceType,clientIp,0, "账号或密码错误");
            return null;
        }

        // 登录逻辑
        SaLoginModel loginModel = SaLoginModel.create()
                .setDevice("PC")
                .setTimeout(tokenTimeout)
                .setIsLastingCookie(true)
                .setExtra("userName", user.getUserName())
                .setExtra("userId", user.getId())
                .setExtra("userAccount", user.getUserAccount());
        StpUtil.login(user.getId(), loginModel);

        // 登录成功后把完整用户信息存入Redis
        String userInfoKey = RedisKeyConst.USER_INFO_KEY + user.getId();
        redisUtil.set(userInfoKey, user, tokenTimeout, TimeUnit.SECONDS);

        AuthUserVO authUserVO = new AuthUserVO();
        BeanUtil.copyProperties(user, authUserVO);
        authUserVO.setSaToken(StpUtil.getTokenValue());
        authUserVO.setExpire(StpUtil.getTokenTimeout());

        // 写入登录日志记录（异步）
        sysLoginLogService.saveLoginLog(user.getUserAccount(), user.getId(),deviceType,clientIp, 1, "登录成功");

        log.info("用户登录成功：userId={},userAccount={}, username={}", user.getId(),user.getUserAccount(), user.getUserName());
        log.info("登录设备 | IP:{} | 设备:{} | 用户:{}", clientIp, deviceType, user.getUserAccount());
        return authUserVO;
    }

    /**
     * 用户登出
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userLogout(LoginDTO dto) {
        Assert.notNull(dto.getId(), "用户ID不能为空");

        String userAccount = String.valueOf(StpUtil.getExtra("userAccount"));

        StpUtil.logout(dto.getId());

        // 修改最后一次登录时间
        lambdaUpdate()
                .set(SysUser::getUpdateTime,LocalDateTime.now())
                .set(SysUser::getLastLoginTime,LocalDateTime.now())
                .setSql("version = version + 1")
                .eq(SysUser::getId,dto.getId())
                .update();

        this.sysLoginLogService.updateLogoutTimeByLoginAccount(userAccount);

        // 清理用户信息
        redisUtil.delete(RedisKeyConst.USER_INFO_KEY + dto.getId());

        log.info("用户登出成功 | userId={} | userAccount={}", dto.getId(), userAccount);
    }

    /**
     * 密码加密（SHA256 + 盐值）
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        return DigestUtil.sha256Hex(UserConst.SALT + userPassword);
    }

    /**
     * 根据ID查询用户
     */
    @Override
    public SysUserVO getUserById(Long id) {
        Assert.notNull(id, "用户ID不能为空");
        SysUser sysUser = this.getById(id);
        if (ObjUtil.isNull(sysUser)) {
            log.warn("查询用户不存在，ID：{}", id);
            return null;
        }
        return BeanUtil.copyProperties(sysUser, SysUserVO.class);
    }

    /**
     * 分页查询用户列表
     */
    @Override
    public IPage<SysUserVO> pageList(UserQueryDTO dto) {
        Assert.notNull(dto, "查询参数不能为空");
        log.info("用户分页查询条件：{}", JSONUtil.toJsonStr(dto));

        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getUserAccount()),SysUser::getUserAccount,dto.getUserAccount());
        wrapper.like(StrUtil.isNotBlank(dto.getUserName()),SysUser::getUserName,dto.getUserName());
        wrapper.eq(StrUtil.isNotBlank(dto.getUserType()),SysUser::getUserType,dto.getUserType());
        wrapper.eq(ObjUtil.isNotNull(dto.getSchoolId()),SysUser::getSchoolId,dto.getSchoolId());
        wrapper.eq(StrUtil.isNotBlank(dto.getStageCode()),SysUser::getStageCode,dto.getStageCode());
        wrapper.like(StrUtil.isNotBlank(dto.getPhone()),SysUser::getPhone,dto.getPhone());
        wrapper.eq(ObjUtil.isNotNull(dto.getStatus()),SysUser::getStatus,dto.getStatus());

        Page<SysUser> page = this.page(dto.getPage(), wrapper);

        // 对象转换
        IPage<SysUserVO> userVOPage = page.convert(user -> BeanUtil.copyProperties(user, SysUserVO.class));

        List<SysUserVO> records = userVOPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return userVOPage;
        }

        // 批量提取关联ID
        Set<Long> schoolIds = new HashSet<>();
        Set<Long> gradeIds = new HashSet<>();
        Set<Long> classIds = new HashSet<>();
        for (SysUserVO vo : records) {
            Optional.ofNullable(vo.getSchoolId()).ifPresent(schoolIds::add);
            Optional.ofNullable(vo.getGradeId()).ifPresent(gradeIds::add);
            Optional.ofNullable(vo.getClassesId()).ifPresent(classIds::add);
        }

        // 批量查询关联数据
        Map<Long, SchoolInfo> schoolMap = this.schoolInfoService.getSchoolMap(schoolIds);
        Map<Long, GradeInfo> gradeMap = this.gradeInfoService.getGradeMap(gradeIds);
        Map<Long, ClassesInfo> classMap = this.classesInfoService.getClassMap(classIds);
        Map<String, SysDict> stageDictMap = this.sysDictService.getDictMap(DictTypeEnum.STAGE);

        // 赋值关联名称
        for (SysUserVO vo : records) {
            this.setUserAssociateNames(vo, schoolMap, gradeMap, classMap, stageDictMap);
        }

        return userVOPage;
    }

    /**
     * 新增用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser create(SysUser entity) {
        Assert.notNull(entity, "用户信息不能为空");
        log.info("新增用户：{}", JSONUtil.toJsonStr(entity));

        // 默认密码
        if(StrUtil.isBlank(entity.getUserPassword())) {
            entity.setUserPassword(getEncryptPassword(UserConst.DEFAULT_PASSWORD));
        }
        // 默认状态：启用
        if(ObjUtil.isEmpty(entity.getStatus())) {
            entity.setStatus(StatusEnum.ENABLED.getCode());
        }
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        this.save(entity);
        return entity;
    }

    /**
     * 更新用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser update(SysUser entity) {
        Assert.notNull(entity, "用户信息不能为空");
        Assert.notNull(entity.getId(), "用户ID不能为空");
        log.info("更新用户：{}", JSONUtil.toJsonStr(entity));

        entity.setUpdateTime(LocalDateTime.now());
        boolean updateSuccess = this.updateById(entity);

        ThrowUtils.throwBusiness(!updateSuccess, "更新用户失败，用户ID：" + entity.getId());

        return this.getById(entity.getId());
    }

    /**
     * 删除用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "用户ID不能为空");
        log.info("删除用户ID：{}", id);
        return this.removeById(id);
    }

    /**
     * 批量重置密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(List<Long> userIds) {
        Assert.notEmpty(userIds, "请选择需要重置密码的用户");
        log.info("批量重置密码，用户IDs：{}", userIds);

        //  统一加密密码
        String encryptPassword = getEncryptPassword(UserConst.DEFAULT_PASSWORD);

        // 批量更新
        return lambdaUpdate()
                .set(SysUser::getUserPassword, encryptPassword)
                .set(SysUser::getUpdateTime,LocalDateTime.now())
                .setSql("version = version + 1") // 👉 手动递增版本号（必须）
                .in(SysUser::getId, userIds)
                .update();
    }

    @Override
    public Map<Long, SysUser> getUserMap(List<Long> ids) {
        if(CollUtil.isEmpty(ids)) {
            return Map.of();
        }
        List<SysUser> sysUsers = this.listByIds(ids);
        if(CollUtil.isEmpty(sysUsers)) {
            return Map.of();
        }
        // 核心：转成 Map<主键id, 用户对象>
        return sysUsers.stream()
                .collect(Collectors.toMap(SysUser::getId, user -> user));
    }

    // ==================== 私有方法：批量查询封装（代码解耦 + 复用）====================
    /**
     * 给用户VO设置关联名称（解耦核心逻辑）
     */
    private void setUserAssociateNames(SysUserVO vo,
                                       Map<Long, SchoolInfo> schoolMap,
                                       Map<Long, GradeInfo> gradeMap,
                                       Map<Long, ClassesInfo> classMap,
                                       Map<String, SysDict> stageDictMap) {
        // 学段名称
        Optional.ofNullable(stageDictMap.get(vo.getStageCode()))
                .ifPresent(dict -> vo.setStageName(dict.getDictName()));

        // 学校名称
        Optional.ofNullable(schoolMap.get(vo.getSchoolId()))
                .ifPresent(school -> vo.setSchoolName(school.getSchoolName()));

        // 年级名称
        Optional.ofNullable(gradeMap.get(vo.getGradeId()))
                .ifPresent(grade -> vo.setGradeName(grade.getGradeName()));

        // 班级名称 + 年级班级组合
        Optional.ofNullable(classMap.get(vo.getClassesId())).ifPresent(classes -> {
            vo.setClassesName(classes.getClassName());
            vo.setGradeAndClass(StrUtil.nullToEmpty(vo.getGradeName()) + classes.getClassName());
        });
    }

    // ==================== 工具方法 ====================
    /**
     * 获取当前请求
     */
    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    /**
     * 获取当前请求的 真实客户端IP（支持Nginx/反向代理）
     */
    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // IPv6 转本地 IPv4
        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            return "127.0.0.1";
        }
        return StrUtil.isBlank(ip) ? "未知IP" : ip.split(",")[0].trim();
    }
    /**
     * 识别客户端类型
     */
    private String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StrUtil.isBlank(userAgent)) return DeviceTypeEnum.PC.getValue();

        if (userAgent.contains("miniProgram") || userAgent.contains("MicroMessenger")) {
            return DeviceTypeEnum.MINI.getValue();
        }
        if (userAgent.contains("okhttp") || userAgent.contains("Android") || userAgent.contains("iPhone")) {
            return DeviceTypeEnum.APP.getValue();
        }
        if (userAgent.contains("Mobile")) {
            return DeviceTypeEnum.H5.getValue();
        }
        return DeviceTypeEnum.PC.getValue();
    }

}
