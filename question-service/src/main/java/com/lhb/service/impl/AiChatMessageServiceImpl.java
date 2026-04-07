package com.lhb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.entity.AiChatMessage;
import com.lhb.mapper.AiChatMessageMapper;
import com.lhb.service.AiChatMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiChatMessageServiceImpl extends ServiceImpl<AiChatMessageMapper, AiChatMessage> implements AiChatMessageService {

    @Override
    public List<AiChatMessage> selectByConversationId(String conversationId, int num) {
        return this.baseMapper.selectByConversationId(conversationId, num);
    }

    @Override
    public int deleteByConversationId(String conversationId) {
        return this.baseMapper.deleteByConversationId(conversationId);
    }

    @Override
    public long maxSeq() {
        return this.count() + 1;
    }

    @Override
    public IPage<AiChatMessage> pageList(long pageNum, long pageSize) {
        return this.page(new PageDTO<>(pageNum, pageSize));
    }

    @Override
    public AiChatMessage create(AiChatMessage entity) {
        this.save(entity);
        return entity;
    }

    @Override
    public AiChatMessage update(AiChatMessage entity) {
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }
}
