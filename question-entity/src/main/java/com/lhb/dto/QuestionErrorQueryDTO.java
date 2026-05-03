package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.QuestionError;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 错题查询 DTO
 */
@Data
public class QuestionErrorQueryDTO extends PageRequest<QuestionError> implements Serializable {

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
