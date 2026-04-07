package com.lhb.enums;

import lombok.Getter;

/**
 * AI 出题批次生成状态（与 t_ai_question_batch.generate_status 对应）
 */
@Getter
public enum GenerateStatusEnum implements BaseEnum {

    PENDING(0,"待生成"),
    GENERATING(1,"生成中"),
    DONE(2,"生成完成"),
    FAILED(3,"生成失败");

    private final int code;
    private final String msg;
    private String value;

    GenerateStatusEnum(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}
