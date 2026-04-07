package com.lhb.utils;

import com.lhb.enums.ErrorCode;
import com.lhb.exception.BusinessException;

import java.sql.SQLException;

/**
 * 异常处理工具类
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition        条件
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 抛业务异常
     *
     * @param errorCode 错误码
     */
    public static void throwBusiness(boolean condition,ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 抛业务异常
     *
     * @param msg 错误信息
     */
    public static void throwBusiness(boolean condition,String msg) {
        throwIf(condition, new BusinessException(ErrorCode.SYSTEM_ERROR,msg));
    }

    /**
     * 抛业务异常
     *
     * @param msg 错误信息
     */
    public static void throwBusiness(String msg) {
        throwIf(true, new BusinessException(ErrorCode.SYSTEM_ERROR,msg));
    }

}
