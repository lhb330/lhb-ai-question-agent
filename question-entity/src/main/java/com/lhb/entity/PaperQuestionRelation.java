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
 * 试卷题目关联实体，对应表 {@code t_paper_question_relation}。
 * <p>维护试卷与题目的多对多关联，含排序和分值。</p>
 */
@Data
@TableName("t_paper_question_relation")
public class PaperQuestionRelation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 试卷 ID */
    private Long paperId;

    /** 题目 ID */
    private Long questionId;

    /** 题目在试卷中的分值 */
    private Integer questionScore;

    /** 题目在试卷中的排序序号 */
    private Integer questionSort;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
