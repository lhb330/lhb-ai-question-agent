package com.lhb.questionweb.chatmemory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.lhb.entity.AiChatMessage;
import com.lhb.service.AiChatMessageService;
import com.lhb.utils.UserUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对话信息保存到数据库表中
 */
@Slf4j
@Component
public class MessageSaveDBChatMemory implements ChatMemory {

    @Resource
    private AiChatMessageService aiChatMessageService;

    @Override
    public void add(String conversationId, List<Message> messages) {
        // 空值校验
        if (StrUtil.isBlankIfStr(conversationId) || CollUtil.isEmpty(messages)) {
            return;
        }

        Long userId = null;
        for (Message msg : messages) {
            if (msg.getMessageType() == MessageType.USER) {
                userId = (Long) msg.getMetadata().get("userId");
                if (userId != null) break;
            }
        }
        if (userId == null) {
            log.warn("未从消息中获取到 userId，conversationId: {}", conversationId);
        }

        // 转换Message为实体类并批量插入
        Long finalUserId = userId;
        List<AiChatMessage> chatMessages = messages.stream().map(message -> {
            AiChatMessage chatMessage = new AiChatMessage();
            chatMessage.setConversationId(conversationId);
            chatMessage.setMessageType(message.getMessageType().name());
            chatMessage.setContent(message.getText());
            String roleType = null;
            switch (message.getMessageType()) {
                case USER -> roleType = "user";
                case ASSISTANT -> roleType = "assistant";
                case SYSTEM -> roleType = "system";
                case TOOL -> roleType = "tool";
                default -> roleType = "unknown";
            }
            chatMessage.setRole(roleType);
            chatMessage.setCreateTime(LocalDateTime.now());
            chatMessage.setUserId(finalUserId);
            return chatMessage;
        }).collect(Collectors.toList());

        // 批量插入数据库
        if (!CollUtil.isEmpty(chatMessages)) {
            aiChatMessageService.getBaseMapper().insert(chatMessages);
        }
    }

    @Override
    public List<Message> get(String conversationId) {
        if (StrUtil.isBlank(conversationId)) {
            return List.of();
        }
        // 查询数据库中的消息
        List<AiChatMessage> chatMessages = aiChatMessageService.selectByConversationId(conversationId,5);
        if (CollUtil.isEmpty(chatMessages)) {
            return List.of();
        }
        // 转换为Spring AI的Message对象
        List<Message> messages = new ArrayList<>();
        for (AiChatMessage chatMessage : chatMessages) {
            Message message = switch (MessageType.valueOf(chatMessage.getMessageType())) {
                case USER -> new org.springframework.ai.chat.messages.UserMessage(chatMessage.getContent());
                case ASSISTANT -> new org.springframework.ai.chat.messages.AssistantMessage(chatMessage.getContent());
                case SYSTEM -> new org.springframework.ai.chat.messages.SystemMessage(chatMessage.getContent());
                // ==============================
                // ✅ 正确构建 ToolResponseMessage（不报错）
                // ==============================
                case TOOL -> ToolResponseMessage.builder()
                        .responses(List.of(
                                new ToolResponseMessage.ToolResponse(
                                        "tool-" + System.currentTimeMillis(),
                                        "unknown_tool",
                                        chatMessage.getContent()
                                )
                        ))
                        .metadata(Map.of())
                        .build();

                default -> new org.springframework.ai.chat.messages.SystemMessage(chatMessage.getContent());
            };
            messages.add(message);
        }

        return messages;
    }

    @Override
    public void clear(String conversationId) {
        if (StrUtil.isNotBlank(conversationId)) {
            aiChatMessageService.deleteByConversationId(conversationId);
        }
    }

}
