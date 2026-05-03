package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.AiChatConversation;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 对话会话查询 DTO
 */
@Data
public class AiChatConversationQueryDTO extends PageRequest<AiChatConversation> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String conversationId;
    private Long userId;
    private String conversationName;
    private String chatType;
    private LocalDateTime lastMsgTime;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
    private String conversationValue;
    private Double importance;
}
