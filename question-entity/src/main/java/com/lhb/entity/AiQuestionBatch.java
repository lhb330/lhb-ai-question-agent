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
 * AI 批量出题任务实体，对应表 {@code t_ai_question_batch}。
 * <p>描述一次出题请求的科目、知识点、题型、数量及生成进度。</p>
 */
@Data
@TableName("t_ai_question_batch")
public class AiQuestionBatch implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 批次编号，业务唯一 */
    private String batchNo;

    /** 创建人用户 ID */
    private Long creatorId;

    /** 科目 ID，关联 {@link SubjectInfo} */
    private Long subjectId;

    /** 学段编码 */
    private String stageCode;

    /** 年级编码，可与字典表关联 */
    private String gradeCode;

    /** 知识点 ID 列表，逗号分隔 */
    private String kpIds;

    /** 题型编码，可与字典表关联 */
    private String questionTypeCode;

    /** 难度编码，可与字典表关联 */
    private String difficultyCode;

    /** 本批次计划生成的题目数量 */
    private Integer questionCount;

    /** 生成状态：0-待生成，1-生成中，2-生成完成，3-生成失败 */
    private Integer generateStatus;

    /** 已成功生成的题目数量 */
    private Integer successCount;

    /** 生成失败的数量 */
    private Integer failCount;

    /** 整体失败或中断时的错误说明 */
    private String errorMsg;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
