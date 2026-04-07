package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysDictVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public SysDictVO(){}

    /** 主键 ID */
    private Long id;

    /** 字典类型（如 grade、难度 difficulty、题型 question_type、学段 stage 等） */
    private String dictType;

    /** 字典编码（如 grade_1、difficulty_2 等） */
    private String dictCode;

    /** 字典显示名称 */
    private String dictName;

    /** 同类型下的排序值，数值越小越靠前 */
    private Integer sort;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 父级字典编码，无父级时为空串 */
    private String parentCode;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    private Long version;

}
