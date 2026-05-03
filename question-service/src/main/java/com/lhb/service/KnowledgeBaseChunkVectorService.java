package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.KnowledgeBaseChunkQueryDTO;
import com.lhb.entity.KnowledgeBaseChunkVector;
import com.lhb.vo.KnowledgeBaseChunkVO;

public interface KnowledgeBaseChunkVectorService extends IService<KnowledgeBaseChunkVector> {

    IPage<KnowledgeBaseChunkVO> pageList(KnowledgeBaseChunkQueryDTO dto);

    KnowledgeBaseChunkVector create(KnowledgeBaseChunkVector entity);

    KnowledgeBaseChunkVector update(KnowledgeBaseChunkVector entity);

    boolean deleteById(Long id);
}
