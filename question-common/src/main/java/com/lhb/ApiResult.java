package com.lhb;

import com.lhb.enums.ErrorCode;
import lombok.Data;

import java.io.Serializable;


/**
 * 统一 API 响应体（无业务模块依赖，供各层复用）
 */
@Data
public class ApiResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private T data;

    public ApiResult() {
        super();
    }

    public ApiResult(int code, String msg, T data) {
        super(code,msg);
        this.data = data;
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResult<T> ok() {
        return ok(null);
    }

    public static <T> ApiResult<T> fail(int code, String msg) {
        return new ApiResult<>(code, msg, null);
    }

    public static <T> ApiResult<T> fail(ErrorCode code, String msg) {
        return new ApiResult<>(code.getCode(), msg, null);
    }

    public static <T> ApiResult<T> fail(ErrorCode code) {
        return new ApiResult<>(code.getCode(), code.getMsg(), null);
    }
}
