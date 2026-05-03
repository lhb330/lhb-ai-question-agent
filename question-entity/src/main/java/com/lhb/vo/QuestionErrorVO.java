package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QuestionErrorVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long questionId;
    private String errorReason;
    private LocalDateTime errorTime;
    private Integer reviewStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
