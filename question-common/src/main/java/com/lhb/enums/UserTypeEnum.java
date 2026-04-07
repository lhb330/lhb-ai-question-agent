package com.lhb.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 用户类型（与 sys_user.user_type 对应）
 */
@Getter
public enum UserTypeEnum implements BaseEnum {

    ADMIN(0,"system_admin","系统管理员"),
    PRINCIPAL(1,"principal","校长"),
    TEACHER(2,"teacher","老师"),
    STUDENT(3,"student","学生");

    private int code;

    private String value;

    private String msg;

    UserTypeEnum(int code,String value, String msg) {
        this.code = code;
        this.value = value;
        this.msg = msg;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值的value
     * @return 枚举值
     */
    public static UserTypeEnum getEnumByValue(String value) {
        for (UserTypeEnum anEnum : UserTypeEnum.values()) {
            if (StrUtil.equals(anEnum.value,value)) {
                return anEnum;
            }
        }
        return null;
    }
}
