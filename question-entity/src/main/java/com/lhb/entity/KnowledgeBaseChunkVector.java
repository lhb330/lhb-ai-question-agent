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
 * 知识库文档分块向量实体，对应表 {@code t_knowledge_base_chunk_vector}。
 * <p>存储知识库文档切片后的文本块及其向量表示，用于 RAG 检索。</p>
 */
@Data
@TableName(value = "t_knowledge_base_chunk_vector", autoResultMap = true)
public class KnowledgeBaseChunkVector implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 关联知识库 ID */
    private Long kbId;

    /** 分块序号 */
    private Integer chunkNo;

    /** 分块文本内容 */
    private String chunkContent;

    /** 分块内容向量表示 */
    @TableField(value = "embedding", typeHandler = FloatArrayToVectorTypeHandler.class)
    private float[] embedding;

    /** 分块大小（字符数） */
    private Integer chunkSize;

    /** 元数据（JSON） */
    @TableField(value = "metadata", typeHandler = MapToJsonTypeHandler.class)
    private Map<String, Object> metadata;

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
