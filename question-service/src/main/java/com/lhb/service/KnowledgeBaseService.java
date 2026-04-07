package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.entity.KnowledgeBase;

import java.util.List;
import java.util.Map;

public interface KnowledgeBaseService extends IService<KnowledgeBase> {

    IPage<KnowledgeBase> pageList(long pageNum, long pageSize);

    KnowledgeBase create(KnowledgeBase entity);

    KnowledgeBase update(KnowledgeBase entity);

    boolean deleteById(Long id);

    Map<Long,KnowledgeBase> getKnowledgeBaseMap(List<Long> ids);
}
