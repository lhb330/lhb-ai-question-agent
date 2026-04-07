package com.lhb.enums;

import lombok.Getter;

/**
 * 登录结果状态（与 sys_login_log.login_status 对应）
 */
@Getter
public enum LoginStatusEnum implements BaseEnum {

    FAIL(0,"失败"),
    SUCCESS(1,"成功");

    private final int code;
    private final String msg;
    private String value;

    LoginStatusEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
