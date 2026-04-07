package com.lhb.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum StageCodeEnum implements BaseEnum{
    PRIMARY(0, "primary",  "小学"),
    JUNIOR(1, "junior", "初中" ),
    SENIOR(2, "senior",  "高中")
    ;

    private int code;

    private String value;

    private String msg;

    StageCodeEnum(int code,String value, String msg) {
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
    public static StageCodeEnum getEnumByValue(String value) {
        for (StageCodeEnum anEnum : StageCodeEnum.values()) {
            if (StrUtil.equals(anEnum.value,value)) {
                return anEnum;
            }
        }
        return null;
    }
}
