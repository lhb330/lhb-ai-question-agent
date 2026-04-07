package com.lhb.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;import java.util.Map;

@Slf4j
@Component
public class JsonUtil {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * JSON字符串 转 Map<String, Object>
     */
    public Map<String, Object> jsonToMap(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty()) {
            return Map.of(); // JDK21 空map
        }
        try {
            // 最标准、最安全的写法
            return objectMapper.readValue(jsonStr, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return Map.of(); // 解析失败返回空map，不抛错
        }
    }

    public List<Map<String, Object>> jsonToListMap(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty()) return List.of();
        try {
            return objectMapper.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            log.error("JSON数组转List<Map>失败", e);
            return List.of();
        }
    }

    public <T> T jsonToClass(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || jsonStr.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.error("JSON转对象失败", e);
            return null;
        }
    }

}
