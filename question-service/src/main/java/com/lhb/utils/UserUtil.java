package com.lhb.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.lhb.constants.RedisKeyConst;
import com.lhb.entity.SysUser;
import com.lhb.enums.ErrorCode;
import com.lhb.exception.BusinessException;

public class UserUtil {

    // 静态持有 RedisUtil
    private static RedisUtil redisUtil() {
        return SpringContextUtil.getBean(RedisUtil.class);
    }

    /**
     * 获取当前登录用户信息（静态方法，直接调用）
     * 使用方式：SysUser user = UserUtil.getLoginUser();
     */
    public static SysUser getLoginUser() {
        // 1. 获取当前登录用户ID
        long userId = StpUtil.getLoginIdAsLong();

        // 2. 从Redis获取用户信息
        String key = RedisKeyConst.USER_INFO_KEY + userId;
        SysUser user = (SysUser) redisUtil().get(key);

        // 3. 未登录/过期
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "登录已过期，请重新登录");
        }

        return user;
    }

    public static SysUser getUserById(Long userId) {
        if (userId == null) {
            return null;
        }
        String key = RedisKeyConst.USER_INFO_KEY + userId;
        return (SysUser) redisUtil().get(key);
    }

}
