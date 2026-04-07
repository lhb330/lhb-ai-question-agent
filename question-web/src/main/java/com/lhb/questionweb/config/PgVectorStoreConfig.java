package com.lhb.questionweb.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PgVectorStoreConfig {


    /**
     * AI生成题目表,题目向量表：t_ai_question_vector
     * @param jdbcTemplate
     * @param dashscopeEmbeddingModel
     * @return
     */
    @Bean("aiQuestionVectorStore")
    public VectorStore aiQuestionVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel dashscopeEmbeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, dashscopeEmbeddingModel)
                .dimensions(1024)
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .indexType(PgVectorStore.PgIndexType.HNSW)
                .initializeSchema(false)
                .schemaName("public")
                .vectorTableName("t_ai_question_vector")
                .idType(PgVectorStore.PgIdType.BIGSERIAL)
                .maxDocumentBatchSize(10000)
                .build();
    }

    /**
     * 知识库向量表：t_knowledge_base_vector
     */
    @Bean("knowledgeBaseVectorStore")
    public VectorStore knowledgeBaseVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel dashscopeEmbeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, dashscopeEmbeddingModel)
                .dimensions(1024)
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE)
                .indexType(PgVectorStore.PgIndexType.HNSW)
                .initializeSchema(false)
                .schemaName("public")
                .vectorTableName("t_knowledge_base")
                .idType(PgVectorStore.PgIdType.BIGSERIAL)
                .maxDocumentBatchSize(10000)
                .build();
    }

}
