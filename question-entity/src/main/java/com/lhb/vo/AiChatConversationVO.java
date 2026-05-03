package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AiChatConversationVO implements Serializable {

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
