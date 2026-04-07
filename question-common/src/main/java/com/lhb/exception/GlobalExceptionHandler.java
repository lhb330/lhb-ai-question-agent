package com.lhb.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.lhb.ApiResult;
import com.lhb.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> businessExceptionHandler(BusinessException e) {
        log.error("业务异常：", e);
        return ApiResult.fail(e.getCode(), e.getMessage());
    }

    /**
     * 其他所有运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("系统异常：", e);
        return ApiResult.fail(ErrorCode.SYSTEM_ERROR, "系统错误: "+e.getMessage());
    }

    /**
     * 参数非法异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<?> runtimeIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常：", e);
        return ApiResult.fail(ErrorCode.PARAMS_ERROR, e.getMessage());
    }

    /**
     * SQL异常
     */
    @ExceptionHandler(SQLException.class)
    public ApiResult<?> sqlException(SQLException e) {
        log.error("sql异常：", e);
        return ApiResult.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 请求方法不支持 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持：", e);
        return ApiResult.fail(e.getStatusCode().value(), "请求方式不正确：" + e.getMessage());
    }

    /**
     * 未登录
     */
    @ExceptionHandler(NotLoginException.class)
    public ApiResult<?> notLoginException(NotLoginException e) {
        log.error("未登录或无效token：", e);
        return ApiResult.fail(ErrorCode.NOT_LOGIN_ERROR);
    }

    @ExceptionHandler(ClassCastException.class)
    public ApiResult<?> errorClassCastException(ClassCastException e) {
        log.error("类转换异常：", e);
        return ApiResult.fail(ErrorCode.SYSTEM_ERROR,"类型转换异常:"+e.getMessage());
    }



}
