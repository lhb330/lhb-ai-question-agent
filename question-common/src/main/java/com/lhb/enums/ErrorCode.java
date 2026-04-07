package com.lhb.enums;

import lombok.Getter;

@Getter
public enum ErrorCode implements BaseEnum {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数格式错误"),
    PARAMS_NULL_ERROR(40200, "必填参数不能为空"),
    NOT_LOGIN_ERROR(40100, "登录已过期，请重新登录"),
    NO_AUTH_ERROR(40101, "暂无此操作权限"),
    AUTH_USER_PWD_ERROR(40102, "账号或密码输入错误"),
    NOT_FOUND_ERROR(40400, "请求数据不存在或已删除"),
    FORBIDDEN_ERROR(40300, "禁止访问该资源"),
    SYSTEM_ERROR(50000, "系统繁忙，请稍后再试"),
    OPERATION_ERROR(50001, "操作失败，请重试"),

    // 用户模块编码
    USER_NOT_FOUND(60000, "用户不存在"),

    // 工具调用自定义码
    TOOL_CALL_ERROR(70000, "功能调用失败"),

    // 大模型调用自定义码
    LLM_CALL_ERROR(80000, "AI服务调用异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String msg;

    private String value;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
