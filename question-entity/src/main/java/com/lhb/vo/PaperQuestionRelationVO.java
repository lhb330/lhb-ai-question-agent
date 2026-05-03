package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PaperQuestionRelationVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long paperId;
    private Long questionId;
    private Integer questionScore;
    private Integer questionSort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
