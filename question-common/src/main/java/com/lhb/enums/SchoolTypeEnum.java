package com.lhb.enums;

import lombok.Getter;

/**
 * 学校类型（与 t_school_info.school_type 对应）
 */
@Getter
public enum SchoolTypeEnum implements BaseEnum {

    HEAD(1,"总校"),
    BRANCH(2,"分校");
    private String value;

    private final int code;
    private final String msg;

    SchoolTypeEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据 code 获取枚举
     *
     * @param code 枚举值的code
     * @return 枚举值
     */
    public static SchoolTypeEnum getEnumByCode(Integer code) {
        for (SchoolTypeEnum anEnum : SchoolTypeEnum.values()) {
            if (code.equals(anEnum.getCode())) {
                return anEnum;
            }
        }
        return null;
    }



}
