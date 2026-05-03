package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.AiChatConversationQueryDTO;
import com.lhb.entity.AiChatConversation;
import com.lhb.vo.AiChatConversationVO;

public interface AiChatConversationService extends IService<AiChatConversation> {

    IPage<AiChatConversationVO> pageList(AiChatConversationQueryDTO dto);

    AiChatConversation create(AiChatConversation entity);

    AiChatConversation update(AiChatConversation entity);

    boolean deleteById(Long id);
}
