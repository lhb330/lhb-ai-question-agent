package com.lhb.vo;

import lombok.Data;

import java.util.List;

@Data
public class GradeInfoGroupVO {

    private Long id;

    private String gradeName;

    private String gradeCode;

    private List<GradeInfoGroupVO> childList;

}
