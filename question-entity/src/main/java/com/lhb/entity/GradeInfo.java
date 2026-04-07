package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_grade_info")
public class GradeInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 年级编码
     */
    private String gradeCode;

    /**
     * 年级名称（一年级、初一、高一等）
     */
    private String gradeName;

    /**
     * 所属学校ID
     */
    private Long schoolId;

    /**
     * 状态（1-启用，0-禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    @TableField("\"version\"")
    @Version
    private Long version;

}