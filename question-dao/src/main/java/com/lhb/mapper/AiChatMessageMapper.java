package com.lhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhb.entity.AiChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AiChatMessageMapper extends BaseMapper<AiChatMessage> {

    /**
     * 根据对话ID查询消息列表
     */
    List<AiChatMessage> selectByConversationId(@Param("conversationId") String conversationId,@Param("num") int num);

    /**
     * 根据对话ID删除消息
     */
    int deleteByConversationId(@Param("conversationId") String conversationId);

}
