package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.lhb.typeHandlers.FloatArrayToVectorTypeHandler;
import com.lhb.typeHandlers.MapToJsonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 知识库文档（含向量）实体，对应表 {@code t_knowledge_base_vector}。
 * <p>存放教材、教辅等切片后的文本及可选向量，供 RAG 或检索使用。</p>
 */
@Data
@TableName(value = "t_knowledge_base",autoResultMap = true)
public class KnowledgeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 知识库条目名称 */
    private String kbName;

    /** 知识库类型，如教材、教辅、真题等 */
    private String kbType;

    /** 科目 ID */
    private Long subjectId;

    /** 学段编码 */
    private String stageCode;

    /** 原始文件名 */
    private String fileName;

    /** 文件存储路径 */
    private String filePath;

    /** 文件大小，单位字节 */
    private Long fileSize;

    /** 文档解析后的文本内容 */
    @TableField("\"content\"")
    private String content;

    /**
     * 文档内容的向量表示（PostgreSQL {@code vector}），未接专用类型时可用字符串占位。
     */
    @TableField(value = "embedding", typeHandler = FloatArrayToVectorTypeHandler.class)
    private String embedding;

    /** 创建人用户 ID */
    private Long creatorId;

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
}
