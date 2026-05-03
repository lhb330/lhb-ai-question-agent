package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.PaperInfo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 试卷查询 DTO
 */
@Data
public class PaperInfoQueryDTO extends PageRequest<PaperInfo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String paperNo;
    private String paperName;
    private Long userId;
    private Long subjectId;
    private String stageCode;
    private String gradeCode;
    private Integer totalScore;
    private Integer questionCount;
    private String paperType;
    private String difficultyRatio;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
