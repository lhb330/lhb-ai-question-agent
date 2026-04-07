package com.lhb.questionweb.auth;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjUtil;
import com.lhb.constants.UserConst;
import com.lhb.entity.SysUser;
import com.lhb.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义权限/角色加载接口（Sa-Token 官方推荐方式）
 * 必须交给Spring管理，否则不生效
 * 控制器使用 @SaCheckRole 注解（直接生效）
 */
@Component
public class CustomRoleChecker implements StpInterface {

    @Resource
    private SysUserService sysUserService; // 注入角色查询服务

    /**
     * 返回指定 loginId 对应的权限列表
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    /**
     * 返回指定 loginId 对应的角色列表（@SaCheckRole 校验时会调用这个方法）
     * @param loginId 登录用户的唯一标识（即 StpUtil.login(loginId) 传入的用户ID）
     * @param loginType 登录类型（默认 "login"）
     * @return 该用户的角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = new ArrayList<>();
        if(ObjUtil.isEmpty(loginId)) {
            return roleList;
        }
        // 核心修改：根据 loginId 从数据库查询真实角色（替换模拟数据）
        // 示例：loginId 是 Long 类型的用户ID，调用服务查角色
        Long userId = Long.parseLong(loginId.toString());
        SysUser sysUser = this.sysUserService.getById(userId);
        if(null == sysUser) {
            return roleList;
        }
        String roleName = UserConst.getUserTypeRoleName(sysUser.getUserType());
        roleList.add(roleName);
        return roleList;
    }

}
