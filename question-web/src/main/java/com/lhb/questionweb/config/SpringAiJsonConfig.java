package com.lhb.questionweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiJsonConfig {

    // 启动时直接修复 Spring AI 内部的 ObjectMapper
    @PostConstruct
    public void fixSpringAiJsonObjectMapper() {
        ObjectMapper objectMapper = org.springframework.ai.util.json.JsonParser.getObjectMapper();
        // 开启容错配置（解决你的 JSON 截断/不规范报错）
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, false);
    }
}