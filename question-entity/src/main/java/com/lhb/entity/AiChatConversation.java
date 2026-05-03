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
 * AI 对话会话实体（长期记忆），对应表 {@code t_ai_chat_conversation}。
 * <p>存储跨会话的长期记忆摘要及重要度评分，支持记忆淘汰策略。</p>
 */
@Data
@TableName("t_ai_chat_conversation")
public class AiChatConversation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 来源会话 ID，追溯记忆来源 */
    private String conversationId;

    /** 用户 ID */
    private Long userId;

    /** 记忆类别/标识 */
    private String conversationName;

    /** 对话类型 */
    private String chatType;

    /** 最后消息时间 */
    private LocalDateTime lastMsgTime;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;

    /** 记忆内容 */
    private String conversationValue;

    /** 重要度评分（0.0~1.0），用于记忆淘汰策略 */
    private Double importance;
}
