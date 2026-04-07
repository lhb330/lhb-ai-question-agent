package com.lhb.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.SysLoginLogDTO;
import com.lhb.entity.SysLoginLog;
import com.lhb.mapper.SysLoginLogMapper;
import com.lhb.service.SysLoginLogService;
import com.lhb.utils.ThrowUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public IPage<SysLoginLog> pageList(SysLoginLogDTO dto) {
        Assert.notNull(dto, "登录日志查询参数不能为空");
        log.info("登录日志分页查询，参数：{}", dto);
        LambdaQueryWrapper<SysLoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getLoginAccount()),SysLoginLog::getLoginAccount,dto.getLoginAccount());
        wrapper.eq(StrUtil.isNotBlank(dto.getLoginDevice()),SysLoginLog::getLoginDevice,dto.getLoginDevice());
        wrapper.orderByDesc(SysLoginLog::getLoginTime);
        return this.page(dto.getPage(),wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysLoginLog create(SysLoginLog entity) {
        Assert.notNull(entity, "登录日志不能为空");
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysLoginLog update(SysLoginLog entity) {
        Assert.notNull(entity, "登录日志实体不能为空");
        Assert.notNull(entity.getId(), "登录日志ID不能为空");
        boolean updateSuccess =  this.updateById(entity);
        if (!updateSuccess) {
            ThrowUtils.throwBusiness(true,"更新登录日志失败，ID：" + entity.getId());
        }
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "日志ID不能为空");
        return this.removeById(id);
    }

    @Override
    public void saveLoginLog(String loginAccount, Long userId,
                             String loginDevice,String ip,
                             Integer loginStatus, String errorMsg) {
        try {
            // 异步执行，不阻塞登录主流程
            CompletableFuture.runAsync(() -> {
                SysLoginLog log = new SysLoginLog();
                log.setUserId(userId);
                log.setLoginAccount(loginAccount);
                log.setLoginIp(ip);
                log.setLoginDevice(loginDevice);
                log.setLoginStatus(loginStatus);
                log.setErrorMsg(errorMsg);
                log.setLoginTime(LocalDateTime.now());
                this.save(log);
            });
        } catch (Exception e) {
            log.error("记录登录日志异常", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLogoutTimeByLoginAccount(String loginAccount) {
        Assert.notBlank(loginAccount,"登录账号不能为空");
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysLoginLog::getLoginAccount, loginAccount)
                .orderByDesc(SysLoginLog::getLoginTime);

        SysLoginLog sysLoginLog = this.getOne(queryWrapper, false);
        if (ObjUtil.isNull(sysLoginLog)) {
            log.warn("未查询到登录日志，无法更新登出时间，账号：{}", loginAccount);
            return;
        }
        sysLoginLog.setLogoutTime(LocalDateTime.now());
        this.updateById(sysLoginLog);
    }
}
