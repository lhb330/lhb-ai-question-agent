package com.lhb.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.cloud.ai.dashscope.embedding.DashScopeEmbeddingModel;
import com.lhb.service.EmbeddingService;
import org.springframework.ai.embedding.Embedding;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 鍚戦噺宓屽叆鏈嶅姟impl
 */
@Service
public class EmbeddingServiceImpl implements EmbeddingService {

    private final DashScopeEmbeddingModel embeddingModel;

    public EmbeddingServiceImpl(DashScopeEmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    public float[] embed(String text) {
        // 鐩存帴璋冪敤 embed 鏂规硶锛屼紶鍏ュ崟涓瓧绗︿覆锛岃繑鍥?Embedding 瀵硅薄
        float[] embed = embeddingModel.embed(text);
        return embed;
    }

}
