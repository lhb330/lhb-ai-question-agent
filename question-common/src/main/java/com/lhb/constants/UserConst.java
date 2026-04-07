package com.lhb.constants;

import cn.hutool.core.util.ObjUtil;
import com.lhb.enums.UserTypeEnum;

public class UserConst {

    /**
     * 加盐，混淆密码
     */
    public static final String SALT = "salt-pwd-lhb";

    /**
     * 默认密码 123456
     */
    public static final String DEFAULT_PASSWORD = "123456";

    public static final String ADMIN = "system_admin";
    public static final String PRINCIPAL = "principal";
    public static final String TEACHER = "teacher";
    public static final String STUDENT = "student";

    /**
     * 获取用户角色
     * @param userType
     * @return
     */
    public static String getUserTypeRoleName(String userType) {
        UserTypeEnum anEnum = UserTypeEnum.getEnumByValue(userType);
        return ObjUtil.isEmpty(anEnum) ? null : anEnum.getValue();
    }

}
