package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PaperInfoVO implements Serializable {

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
