package com.lhb.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.entity.KnowledgeBase;
import com.lhb.mapper.KnowledgeBaseMapper;
import com.lhb.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseServiceImpl extends ServiceImpl<KnowledgeBaseMapper, KnowledgeBase> implements KnowledgeBaseService {

    @Override
    public IPage<KnowledgeBase> pageList(long pageNum, long pageSize) {
        return this.page(new PageDTO<>(pageNum, pageSize));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeBase create(KnowledgeBase entity) {
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeBase update(KnowledgeBase entity) {
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }

    @Override
    public Map<Long, KnowledgeBase> getKnowledgeBaseMap(List<Long> ids) {
        if(CollUtil.isEmpty(ids)) {
            return Map.of();
        }
        List<KnowledgeBase> list = this.listByIds(ids);
        if(CollUtil.isEmpty(list)) {
            return Map.of();
        }
        return list.stream()
                .collect(Collectors.toMap(KnowledgeBase::getId, s -> s));
    }
}
