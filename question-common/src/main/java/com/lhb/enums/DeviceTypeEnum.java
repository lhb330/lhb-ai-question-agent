package com.lhb.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum DeviceTypeEnum implements BaseEnum {
    PC(0,"PC","电脑端"),
    MINI(1,"MINI","微信小程序"),
    APP(2,"APP","手机应用"),
    H5(3,"H5","手机网页")
    ;


    private int code;

    private String value;

    private String msg;

    DeviceTypeEnum(int code, String value, String msg) {
        this.code = code;
        this.value = value;
        this.msg = msg;
    }
}
