package com.lhb.questionweb.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用于学段核验与知识库合规性检查的工具。
 * Tool for stage validation and knowledge-base compliance checks.
 */
@Component
public class QuestionCheckTool {

    private static final Pattern PRIMARY_RISK = Pattern.compile("(导数|积分|函数单调性|抛物线|电磁感应|有机化学|概率分布)");
    private static final Pattern MIDDLE_RISK = Pattern.compile("(泰勒|极限|洛必达|矩阵|量子|热力学第二定律)");

    private final VectorSearchBaseTool vectorSearchBaseTool;

    public QuestionCheckTool(VectorSearchBaseTool vectorSearchBaseTool) {
        this.vectorSearchBaseTool = vectorSearchBaseTool;
    }

    @Tool(
            name = "questionStageCheck",
            description = "验证问题难度是否与要求的阶段相匹配。"
    )
    public Map<String, Object> checkStage(
            @ToolParam(description = "题目内容 / 问题内容", required = true) String content,
            @ToolParam(description = "学段代码，例如：小学 / 初中 / 高中", required = true) String stageCode
    ) {
        String normalizedStage = normalizeStage(stageCode);
        boolean pass = evaluateStage(content, normalizedStage);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pass", pass);
        result.put("stageCode", normalizedStage);
        result.put("message", pass ? "题目难度与学段匹配" : "题目难度疑似超出学段");
        return result;
    }

    @Tool(
            name = "check_question_compliance",
            description = "验证该题目是否处于目标学段范围内，且在知识库覆盖范围内。"
    )
    public Map<String, Object> checkCompliance(
            @ToolParam(description = "题目内容 / 问题内容", required = true) String content,
            @ToolParam(description = "科目id", required = false) Long subjectId,
            @ToolParam(description = "学段编码", required = true) String stageCode
    ) {
        String normalizedStage = normalizeStage(stageCode);
        boolean stagePass = evaluateStage(content, normalizedStage);

        List<VectorSearchBaseTool.SimilarityHit> kbHits =
                vectorSearchBaseTool.searchSimilarKnowledge(content, subjectId, normalizedStage, 5);

        boolean knowledgePass = kbHits.stream()
                .anyMatch(hit -> hit.distance() <= vectorSearchBaseTool.getKnowledgeThreshold());

        boolean pass = stagePass && knowledgePass;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pass", pass);
        result.put("stagePass", stagePass);
        result.put("knowledgePass", knowledgePass);
        result.put("knowledgeThreshold", vectorSearchBaseTool.getKnowledgeThreshold());
        result.put("message", buildMessage(stagePass, knowledgePass));
        result.put("evidence", toEvidence(kbHits));
        return result;
    }

    private boolean evaluateStage(String content, String stageCode) {
        if (!StringUtils.hasText(content)) {
            return false;
        }
        String normalized = stageCode == null ? "" : stageCode.trim().toLowerCase();
        if ("primary".equals(normalized) || "小学".equals(normalized)) {
            return !PRIMARY_RISK.matcher(content).find();
        }
        if ("middle".equals(normalized) || "初中".equals(normalized)) {
            return !MIDDLE_RISK.matcher(content).find();
        }
        return true;
    }

    private String normalizeStage(String stageCode) {
        if (!StringUtils.hasText(stageCode)) {
            return "";
        }
        String value = stageCode.trim().toLowerCase();
        if (value.contains("小学") || "primary".equals(value)) {
            return "primary";
        }
        if (value.contains("初中") || "middle".equals(value) || value.contains("junior")) {
            return "middle";
        }
        if (value.contains("高中") || "high".equals(value) || value.contains("senior")) {
            return "high";
        }
        return value;
    }

    private String buildMessage(boolean stagePass, boolean knowledgePass) {
        if (stagePass && knowledgePass) {
            return "校验通过";
        }
        if (!stagePass && !knowledgePass) {
            return "学段与知识库校验均未通过";
        }
        if (!stagePass) {
            return "学段校验未通过";
        }
        return "知识库范围校验未通过";
    }

    private List<Map<String, Object>> toEvidence(List<VectorSearchBaseTool.SimilarityHit> hits) {
        return hits.stream().map(hit -> {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", hit.id());
            item.put("kbName", hit.bizNo());
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
