package com.lhb.vo;

import lombok.Data;

import java.util.List;

@Data
public class SubjectGroupVO {

    private Long id;

    private String subjectName;

    private Long parentId;

    private String parentSubjectCode;

    private String stageCode;

    private String stageName;

    private Integer sort;

    List<SubjectGroupVO> childList;
}