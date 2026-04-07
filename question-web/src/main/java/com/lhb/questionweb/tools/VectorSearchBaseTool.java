package com.lhb.questionweb.tools;

import com.lhb.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 用于试题与知识库表的共享向量检索工具
 * Shared vector-search utilities for question and knowledge-base tables.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VectorSearchBaseTool {

    private static final int DEFAULT_TOP_K = 5;

    private final EmbeddingService embeddingService;
    private final JdbcTemplate jdbcTemplate;

    @Value("${app.vector.duplicate-threshold:0.2}")
    private double duplicateThreshold;

    @Value("${app.vector.knowledge-threshold:0.35}")
    private double knowledgeThreshold;

    public List<SimilarityHit> searchSimilarQuestions(String content, Long subjectId, String stageCode, Integer topK) {
        if (!StringUtils.hasText(content)) {
            return Collections.emptyList();
        }
        return searchQuestionByVector(content, subjectId, stageCode, normalizeTopK(topK));
    }

    public List<SimilarityHit> searchSimilarKnowledge(String content, Long subjectId, String stageCode, Integer topK) {
        if (!StringUtils.hasText(content)) {
            return Collections.emptyList();
        }
        return searchKnowledgeByVector(content, subjectId, stageCode, normalizeTopK(topK));
    }

    public boolean isDuplicateQuestion(String content, Long subjectId, String stageCode) {
        List<SimilarityHit> hits = searchSimilarQuestions(content, subjectId, stageCode, DEFAULT_TOP_K);
        if (hits.isEmpty()) {
            return false;
        }
        return hits.stream().anyMatch(hit -> hit.distance() <= duplicateThreshold);
    }

    public boolean matchesKnowledgeBase(String content, Long subjectId, String stageCode) {
        List<SimilarityHit> hits = searchSimilarKnowledge(content, subjectId, stageCode, DEFAULT_TOP_K);
        if (hits.isEmpty()) {
            return false;
        }
        return hits.stream().anyMatch(hit -> hit.distance() <= knowledgeThreshold);
    }

    public double getDuplicateThreshold() {
        return duplicateThreshold;
    }

    public double getKnowledgeThreshold() {
        return knowledgeThreshold;
    }

    private List<SimilarityHit> searchQuestionByVector(String content, Long subjectId, String stageCode, int topK) {
        float[] embedding = safeEmbed(content);
        if (embedding == null || embedding.length == 0) {
            return searchQuestionByKeyword(content, subjectId, stageCode, topK);
        }

        String sql = """
                SELECT id,
                       question_no AS biz_no,
                       content,
                       (embedding <=> CAST(? AS vector)) AS distance
                FROM t_ai_question_vector
                WHERE status = 1
                  AND embedding IS NOT NULL
                  AND (? IS NULL OR subject_id = ?)
                  AND (? IS NULL OR stage_code = ?)
                ORDER BY embedding <=> CAST(? AS vector)
                LIMIT ?
                """;

        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapHit(rs),
                vectorLiteral,
                subjectId,
                subjectId,
                stageCode,
                stageCode,
                vectorLiteral,
                topK
        );
    }

    private List<SimilarityHit> searchKnowledgeByVector(String content, Long subjectId, String stageCode, int topK) {
        float[] embedding = safeEmbed(content);
        if (embedding == null || embedding.length == 0) {
            return searchKnowledgeByKeyword(content, subjectId, stageCode, topK);
        }

        String sql = """
                SELECT id,
                       kb_name AS biz_no,
                       content,
                       (embedding <=> CAST(? AS vector)) AS distance
                FROM t_knowledge_base
                WHERE status = 1
                  AND embedding IS NOT NULL
                  AND (? IS NULL OR subject_id = ?)
                  AND (? IS NULL OR stage_code = ?)
                ORDER BY embedding <=> CAST(? AS vector)
                LIMIT ?
                """;

        String vectorLiteral = toVectorLiteral(embedding);
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapHit(rs),
                vectorLiteral,
                subjectId,
                subjectId,
                stageCode,
                stageCode,
                vectorLiteral,
                topK
        );
    }

    private List<SimilarityHit> searchQuestionByKeyword(String content, Long subjectId, String stageCode, int topK) {
        String sql = """
                SELECT id,
                       question_no AS biz_no,
                       content,
                       1.0 AS distance
                FROM t_ai_question_vector
                WHERE status = 1
                  AND (? IS NULL OR subject_id = ?)
                  AND (? IS NULL OR stage_code = ?)
                  AND content ILIKE ?
                ORDER BY update_time DESC
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapHit(rs),
                subjectId,
                subjectId,
                stageCode,
                stageCode,
                "%" + content.trim() + "%",
                topK
        );
    }

    private List<SimilarityHit> searchKnowledgeByKeyword(String content, Long subjectId, String stageCode, int topK) {
        String sql = """
                SELECT id,
                       kb_name AS biz_no,
                       content,
                       1.0 AS distance
                FROM t_knowledge_base
                WHERE status = 1
                  AND (? IS NULL OR subject_id = ?)
                  AND (? IS NULL OR stage_code = ?)
                  AND content ILIKE ?
                ORDER BY update_time DESC
                LIMIT ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapHit(rs),
                subjectId,
                subjectId,
                stageCode,
                stageCode,
                "%" + content.trim() + "%",
                topK
        );
    }

    private float[] safeEmbed(String text) {
        try {
            return embeddingService.embed(text);
        }
        catch (Exception ex) {
            log.warn("Embedding failed, fallback to keyword retrieval. reason={}", ex.getMessage());
            return null;
        }
    }

    private SimilarityHit mapHit(ResultSet rs) throws SQLException {
        return new SimilarityHit(
                rs.getLong("id"),
                rs.getString("biz_no"),
                rs.getString("content"),
                rs.getDouble("distance")
        );
    }

    private int normalizeTopK(Integer topK) {
        if (topK == null || topK <= 0) {
            return DEFAULT_TOP_K;
        }
        return Math.min(topK, 20);
    }

    private String toVectorLiteral(float[] vector) {
        List<String> values = new ArrayList<>(vector.length);
        for (float v : vector) {
            values.add(String.format(Locale.ROOT, "%s", v));
        }
        return "[" + String.join(",", values) + "]";
    }

    public record SimilarityHit(Long id, String bizNo, String content, Double distance) {
    }
}
