package com.lhb;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public BaseResult() {

    }

    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
