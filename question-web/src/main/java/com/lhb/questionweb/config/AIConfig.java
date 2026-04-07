package com.lhb.questionweb.config;

import com.alibaba.cloud.ai.graph.agent.Agent;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.lhb.questionweb.advisor.MyLoggerAdvisor;
import com.lhb.questionweb.chatmemory.MessageSaveDBChatMemory;
import com.lhb.questionweb.tools.QuestionCheckTool;
import com.lhb.questionweb.tools.QuestionDuplicateCheckTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI core configuration.
 */
@Configuration
@RequiredArgsConstructor
public class AIConfig {

    private static final String QUESTION_SYSTEM_PROMPT = """
            你是专业的中小学出题AI，请严格遵守以下规则：
            1. 必须按照用户指定的【学段、学科、知识点、题型、难度】生成题目。
            2. 出题前必须调用工具进行校验：
               - 先调用 questionStageCheck 或 check_question_compliance 校验学段与知识范围；
               - 再调用 questionDuplicateCheck 进行重复题检测；
               - 只要任一工具返回不通过，必须重新生成并再次校验，直到通过。
            3. 输出必须是纯 JSON，对象中仅包含字段：
               - content（题目）
               - answer（答案）
               - analysis（解析）
            4. 禁止输出违规、超纲、与学段不匹配的内容。
            5. 难度必须匹配用户要求：
               - 简单：基础题
               - 中等：提升题
               - 困难：拓展题
               - 极难：拔高挑战题
            """;

    private final MessageSaveDBChatMemory chatMemory;
    private final ChatModel dashscopeChatModel;
    private final QuestionCheckTool questionCheckTool;
    private final QuestionDuplicateCheckTool questionDuplicateCheckTool;

    @Bean("questionToolCallbacks")
    public ToolCallback[] questionToolCallbacks() {
        MethodToolCallbackProvider toolProvider = MethodToolCallbackProvider.builder()
                .toolObjects(questionCheckTool, questionDuplicateCheckTool)
                .build();
        return toolProvider.getToolCallbacks();
    }

    /**
     * 可选的智能代理 Bean，用于后续扩展与调试。
     */
    @Bean
    public Agent questionAgent(ToolCallback[] questionToolCallbacks) {
        return ReactAgent.builder()
                .name("question-agent")
                .model(dashscopeChatModel)
                .tools(questionToolCallbacks)
                .systemPrompt(QUESTION_SYSTEM_PROMPT)
                .build();
    }

    /**
     * ChatClient 必须通过 ChatModel 构建（而非通过 Agent 构建）。
     */
    @Bean
    public ChatClient chatClient() {
        return ChatClient.builder(dashscopeChatModel)
                .defaultSystem(QUESTION_SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        MyLoggerAdvisor.builder().build()
                )
                .build();
    }
}
