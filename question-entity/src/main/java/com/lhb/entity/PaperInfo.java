package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 试卷信息实体，对应表 {@code t_paper_info}。
 * <p>记录用户组卷后的试卷基本信息。</p>
 */
@Data
@TableName("t_paper_info")
public class PaperInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 试卷编号，唯一 */
    private String paperNo;

    /** 试卷名称 */
    private String paperName;

    /** 创建用户 ID */
    private Long userId;

    /** 科目 ID */
    private Long subjectId;

    /** 学段编码 */
    private String stageCode;

    /** 年级编码 */
    private String gradeCode;

    /** 试卷总分，默认 100 */
    private Integer totalScore;

    /** 题目数量 */
    private Integer questionCount;

    /** 试卷类型 */
    private String paperType;

    /** 难度比例，如 "3:4:3" 表示易:中:难 */
    private String difficultyRatio;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
