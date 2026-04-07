package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.entity.AiChatMessage;

import java.util.List;

public interface AiChatMessageService extends IService<AiChatMessage> {

    IPage<AiChatMessage> pageList(long pageNum, long pageSize);

    AiChatMessage create(AiChatMessage entity);

    AiChatMessage update(AiChatMessage entity);

    boolean deleteById(Long id);

    List<AiChatMessage> selectByConversationId(String conversationId, int num);

    int deleteByConversationId(String conversationId);

    long maxSeq();
}
