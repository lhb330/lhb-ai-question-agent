package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.QuestionCollect;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 题目收藏查询 DTO
 */
@Data
public class QuestionCollectQueryDTO extends PageRequest<QuestionCollect> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private Long questionId;
    private String collectNote;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
