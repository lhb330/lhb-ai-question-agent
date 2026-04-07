package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.entity.AiQuestionBatch;

public interface AiQuestionBatchService extends IService<AiQuestionBatch> {

    IPage<AiQuestionBatch> pageList(long pageNum, long pageSize);

    AiQuestionBatch create(AiQuestionBatch entity);

    AiQuestionBatch update(AiQuestionBatch entity);

    boolean deleteById(Long id);
}
