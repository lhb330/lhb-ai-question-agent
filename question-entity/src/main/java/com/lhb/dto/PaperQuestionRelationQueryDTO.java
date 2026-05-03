package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.PaperQuestionRelation;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 试卷题目关联查询 DTO
 */
@Data
public class PaperQuestionRelationQueryDTO extends PageRequest<PaperQuestionRelation> implements Serializable {

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
