package com.lhb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.entity.AiQuestionBatch;
import com.lhb.mapper.AiQuestionBatchMapper;
import com.lhb.service.AiQuestionBatchService;
import org.springframework.stereotype.Service;

@Service
public class AiQuestionBatchServiceImpl extends ServiceImpl<AiQuestionBatchMapper, AiQuestionBatch> implements AiQuestionBatchService {

    @Override
    public IPage<AiQuestionBatch> pageList(long pageNum, long pageSize) {
        return this.page(new PageDTO<>(pageNum, pageSize));
    }

    @Override
    public AiQuestionBatch create(AiQuestionBatch entity) {
        this.save(entity);
        return entity;
    }

    @Override
    public AiQuestionBatch update(AiQuestionBatch entity) {
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }
}
