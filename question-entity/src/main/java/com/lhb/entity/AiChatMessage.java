package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * AI 对话消息实体，对应表 {@code t_ai_chat_message}。
 * <p>按会话维度存储用户与助手的多轮消息，便于追溯与审计。</p>
 */
@Data
@TableName("t_ai_chat_message")
public class AiChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 会话 ID，同一会话内多条消息共享 */
    private String conversationId;

    /** 消息类型，如 USER、ASSISTANT、SYSTEM 等 */
    private String messageType;

    /** 消息正文 */
    @TableField("\"content\"")
    private String content;

    /** 角色：user、assistant、system 等，与模型协议对齐 */
    @TableField("\"role\"")
    private String role;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;

    private Long userId;
}
