package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 班级表
 */
@Data
@TableName("t_classes_info")
public class ClassesInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 班级编码
     */
    private String classCode;

    /**
     * 班级名称（1班、实验班、凌云班等）
     */
    private String className;

    /**
     * 所属年级ID
     */
    private Long gradeId;

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