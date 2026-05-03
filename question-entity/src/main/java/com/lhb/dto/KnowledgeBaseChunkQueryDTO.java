package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.KnowledgeBaseChunkVector;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 知识库分块向量查询 DTO
 */
@Data
public class KnowledgeBaseChunkQueryDTO extends PageRequest<KnowledgeBaseChunkVector> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long kbId;
    private Integer chunkNo;
    private String chunkContent;
    private Integer chunkSize;
    private Map<String, Object> metadata;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
