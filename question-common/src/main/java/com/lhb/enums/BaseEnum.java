package com.lhb.enums;

public interface BaseEnum {

    /**
     * 获取状态码
     * @return 状态码数值
     */
    int getCode();

    /**
     * 获取状态码字符串
     * @return 状态码字符串
     */
    String getValue();

    /**
     * 获取提示信息
     * @return 提示信息字符串
     */
    String getMsg();

}
