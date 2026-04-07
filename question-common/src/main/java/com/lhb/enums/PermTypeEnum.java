package com.lhb.enums;

import lombok.Getter;

/**
 * 权限类型（与 sys_permission.perm_type 对应）
 */
@Getter
public enum PermTypeEnum implements BaseEnum {

    BUTTON(1,"按钮");

    private final int code;
    private final String msg;
    private String value;

    PermTypeEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
