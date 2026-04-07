package com.lhb.enums;

import lombok.Getter;

/**
 * 通用状态
 */
@Getter
public enum StatusEnum {

    DISABLED(0,"禁用"),
    ENABLED(1,"启用");

    private int code;
    private String msg;


    StatusEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }



}
