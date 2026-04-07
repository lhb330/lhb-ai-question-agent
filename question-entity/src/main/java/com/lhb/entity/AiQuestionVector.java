package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.lhb.typeHandlers.FloatArrayToVectorTypeHandler;
import com.lhb.typeHandlers.MapToJsonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * AI 生成的题目（含向量）实体，对应表 {@code t_ai_question_vector}。
 * <p>存储题干、解析、答案及可选的向量字段，用于检索或去重等场景。</p>
 */
@Data
@TableName(value = "t_ai_question_vector",autoResultMap = true)
public class AiQuestionVector implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 题目编号，业务唯一 */
    private String questionNo;

    /** 科目 ID */
    private Long subjectId;

    /** 学段编码 可与字典表关联*/
    private String stageCode;

    /** 年级id */
    private Long gradeId;

    /** 知识点 ID */
    private Long kpId;

    /** 题型编码，可与字典表关联 */
    private String questionTypeCode;

    /** 难度编码，可与字典表关联 */
    private String difficultyCode;

    /** 题目正文（题干） */
    @TableField("\"content\"")
    private String content;

    /** 题目解析 */
    private String questionAnalysis;

    /** 参考答案或标准答案 */
    private String questionAnswer;

    /**
     * 题目内容在数据库中的向量表示（PostgreSQL {@code vector}）。
     * 当前以字符串承接；接入 pgvector 与专用 TypeHandler 后可改为更合适的 Java 类型。
     */
    @TableField(value = "embedding", typeHandler = FloatArrayToVectorTypeHandler.class)
    private float[] embedding;

    /** 创建人用户 ID（老师或校长） */
    private Long creatorId;

    /** 所属生成批次号，与 {@link AiQuestionBatch#batchNo} 对应 */
    private String generateBatchNo;

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

    @TableField(value = "metadata", typeHandler = MapToJsonTypeHandler.class)
    private Map<String,Object> metadata;

    /** 题目分值 */
    private Long questionScore;

    /** 来源知识库ID */
    private Long sourceKbId;
}
