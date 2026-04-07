package com.lhb.questionweb.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 试题重复检测工具
 * Tool for duplicate question detection.
 */
@Component
@RequiredArgsConstructor
public class QuestionDuplicateCheckTool {

    private final VectorSearchBaseTool vectorSearchBaseTool;

    @Tool(
            name = "questionDuplicateCheck",
            description = "检测所生成的题目是否与现有题目在语义上重复。"
    )
    public Map<String, Object> checkDuplicate(
            @ToolParam(description = "题目内容 / 问题内容", required = true) String content,
            @ToolParam(description = "科目id", required = false) Long subjectId,
            @ToolParam(description = "学段编码", required = false) String stageCode
    ) {
        List<VectorSearchBaseTool.SimilarityHit> hits =
                vectorSearchBaseTool.searchSimilarQuestions(content, subjectId, stageCode, 5);

        boolean duplicate = hits.stream().anyMatch(hit -> hit.distance() <= vectorSearchBaseTool.getDuplicateThreshold());

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pass", !duplicate);
        result.put("duplicate", duplicate);
        result.put("threshold", vectorSearchBaseTool.getDuplicateThreshold());
        result.put("message", duplicate ? "题目与历史题目语义重复" : "未发现重复题目");
        result.put("topMatches", toMatchList(hits));
        return result;
    }

    private List<Map<String, Object>> toMatchList(List<VectorSearchBaseTool.SimilarityHit> hits) {
        return hits.stream().map(hit -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", hit.id());
            item.put("bizNo", hit.bizNo());
            item.put("distance", hit.distance());
            item.put("content", shorten(hit.content()));
            return item;
        }).collect(Collectors.toList());
    }

    private String shorten(String text) {
        if (text == null || text.length() <= 120) {
            return text;
        }
        return text.substring(0, 120) + "...";
    }
}
