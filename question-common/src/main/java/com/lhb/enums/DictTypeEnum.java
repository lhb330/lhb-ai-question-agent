package com.lhb.enums;

import lombok.Getter;

/**
 * 字典类型（与 sys_dict.dict_type 对应）
 */
@Getter
public enum DictTypeEnum {

    STAGE("stage","学段"),
    ROLE("role","系统角色"),
    QUESTION_TYPE("question_type","题目类型"),
    DIFFICULTY("difficulty","题型难度"),
    STATUS("status","状态"),




    OTHER("other","其它");


    public final String dictType;

    public final String dictName;

    DictTypeEnum(String dictType, String dictName) {
        this.dictType = dictType;
        this.dictName = dictName;
    }
}
