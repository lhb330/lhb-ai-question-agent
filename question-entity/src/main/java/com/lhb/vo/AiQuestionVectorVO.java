package com.lhb.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lhb.entity.AiQuestionBatch;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class AiQuestionVectorVO {

    private Long id;

    /** 题目编号，业务唯一 */
    private String questionNo;

    /** 科目 ID */
    private Long subjectId;
    private String subjectName;

    /** 学段编码 可与字典表关联*/
    private String stageCode;

    /** 年级id */
    private Long gradeId;
    private String gradeName;

    /** 知识点 ID */
    private Long kpId;
    private String kpName;

    /** 题型编码，可与字典表关联 */
    private String questionTypeCode;

    /** 难度编码，可与字典表关联 */
    private String difficultyCode;

    /** 题目正文（题干） */
    private String content;

    /** 题目解析 */
    private String questionAnalysis;

    /** 参考答案或标准答案 */
    private String questionAnswer;

    /**
     * 题目内容在数据库中的向量表示（PostgreSQL {@code vector}）。
     * 当前以字符串承接；接入 pgvector 与专用 TypeHandler 后可改为更合适的 Java 类型。
     */
    private float[] embedding;

    /** 创建人用户 ID（老师或校长） */
    private Long creatorId;
    private String creatorName;

    /** 所属生成批次号，与 {@link AiQuestionBatch#batchNo} 对应 */
    private String generateBatchNo;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    private Long version;

    private Map<String,Object> metadata;

    /** 题目分值 */
    private Long questionScore;

    /** 来源知识库ID */
    private Long sourceKbId;
    private String sourceKbName;

}
