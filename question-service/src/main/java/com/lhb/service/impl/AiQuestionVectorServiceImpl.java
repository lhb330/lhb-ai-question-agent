package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhb.constants.SysConstants;
import com.lhb.dto.AiQuestionVectorDTO;
import com.lhb.entity.*;
import com.lhb.enums.DictTypeEnum;
import com.lhb.mapper.AiQuestionVectorMapper;
import com.lhb.prompt.UserPrompt;
import com.lhb.service.*;
import com.lhb.utils.JsonUtil;
import com.lhb.utils.ThrowUtils;
import com.lhb.utils.UserUtil;
import com.lhb.vo.AiQuestionVectorVO;
import com.lhb.vo.KnowledgePointVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AiQuestionVectorServiceImpl extends ServiceImpl<AiQuestionVectorMapper, AiQuestionVector> implements AiQuestionVectorService {

    @Resource
    private EmbeddingService embeddingService;

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private GradeInfoService gradeInfoService;

    @Resource
    private KnowledgePointService knowledgePointService;

    @Resource
    private KnowledgeBaseService knowledgeBaseService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private AiChatMessageService aiChatMessageService;

    @Resource
    private ChatClient chatClient;

    @Resource
    private ToolCallback[] questionToolCallbacks;

    @Resource
    private UserPrompt userPrompt;

    @Resource
    private JsonUtil jsonUtil;

    @Resource
    private SysDictService sysDictService;

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void saveAiQuestionVector(AiQuestionVectorDTO dto, Long userId) {
        try {
            // ==========================================
            // ✅ 异步里 直接从 Redis 拿用户
            // ==========================================
            SysUser loginUser = UserUtil.getUserById(userId);
            long maxSeq = aiChatMessageService.maxSeq();
            String convId = SysConstants.generateConversationId(maxSeq);
            String buildUserPrompt = userPrompt.aiQuestionUserPrompt(dto);
            String generateCode = SysConstants.generateCode(this.maxSeq());

            String modelOutput = chatClient.prompt()
                    .advisors(spec ->{
                        spec.param(ChatMemory.CONVERSATION_ID, convId);
                        spec.param("userId",userId);
                    })
                    .user(userSpec -> userSpec.text(buildUserPrompt).metadata("userId", userId))
                    .toolCallbacks(questionToolCallbacks)
                    .call()
                    .content();
            List<Map<String,Object>> parseListMap = new LinkedList<>();
            // 一道题，大模型返回数据结构是: {a:xxxx,b:xxxx,c:xxxx}
            if(dto.getQuestionCount().equals(1L)) {
                Map<String, Object> jsonToMap = jsonUtil.jsonToMap(modelOutput);
                parseListMap.add(jsonToMap);
            }else {
                parseListMap = jsonUtil.jsonToListMap(modelOutput);
            }
            List<AiQuestionVector> list = new LinkedList<>();
            for (int i = 0; i < parseListMap.size(); i++) {
                Map<String, Object> map = parseListMap.get(i);
                AiQuestionVector entity = new AiQuestionVector();
                entity.setQuestionNo(generateCode + '-' + (i+1));
                entity.setSubjectId(dto.getSubjectId());
                entity.setStageCode(dto.getStageCode());
                entity.setGradeId(dto.getGradeId());
                entity.setKpId(dto.getKpId());
                entity.setQuestionTypeCode(dto.getQuestionTypeCode());
                entity.setDifficultyCode(dto.getDifficultyCode());
                entity.setContent(map.getOrDefault("content","").toString());
                entity.setQuestionAnswer(map.getOrDefault("answer","").toString());
                entity.setQuestionAnalysis(map.getOrDefault("analysis","").toString());
                entity.setEmbedding(this.embeddingService.embed(entity.getContent()));
                entity.setCreatorId(loginUser.getId());

                Map<String, Object> metadata = new LinkedHashMap<>();
                metadata.put("conversationId", convId);
                KnowledgePointVO kpVO = knowledgePointService.getById(dto.getKpId());
                metadata.put("knowledgePoint", kpVO.getKpName());
                metadata.put("source", "api-question-generate");
                metadata.put("questionSource", "AI自动生成");
                Map<String,SysDict> difficultyMap = sysDictService.getDictMap(DictTypeEnum.DIFFICULTY);
                metadata.put("difficulty", difficultyMap.get(dto.getDifficultyCode()).getDictName());

                Map<String,SysDict> dictMap = sysDictService.getDictMap(DictTypeEnum.QUESTION_TYPE);
                metadata.put("questionType", dictMap.get(dto.getQuestionTypeCode()).getDictName());

                entity.setMetadata(metadata);


                list.add(entity);
            }
            if(CollUtil.isNotEmpty(list)) {
                this.saveBatch(list);
            }
            log.info("✅ AI题目异步生成完成：{}", generateCode);
        } catch (Exception e) {
            log.error("❌ AI题目生成失败", e);
            ThrowUtils.throwBusiness("AI题目生成失败");
        }
    }

    @Override
    public long maxSeq() {
        return this.count() + 1;
    }

    @Override
    public IPage<AiQuestionVectorVO> pageList(AiQuestionVectorDTO dto) {
        LambdaQueryWrapper<AiQuestionVector> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(null != dto.getSubjectId(),AiQuestionVector::getSubjectId,dto.getSubjectId())
                .eq(null != dto.getGradeId(),AiQuestionVector::getGradeId,dto.getGradeId())
                .eq(StrUtil.isNotBlank(dto.getStageCode()),AiQuestionVector::getStageCode,dto.getStageCode())
                .eq(null != dto.getKpId(),AiQuestionVector::getKpId,dto.getKpId())
                .eq(StrUtil.isNotBlank(dto.getQuestionTypeCode()),AiQuestionVector::getQuestionTypeCode,dto.getQuestionTypeCode())
                .eq(StrUtil.isNotBlank(dto.getDifficultyCode()),AiQuestionVector::getDifficultyCode,dto.getDifficultyCode())
                .eq(null != dto.getSourceKbId(),AiQuestionVector::getSourceKbId,dto.getSourceKbId())
                .eq(null != dto.getStatus(),AiQuestionVector::getStatus,dto.getStatus())
                .orderByDesc(AiQuestionVector::getUpdateTime);

        Page<AiQuestionVector> page = this.page(dto.getPage(), wrapper);

        IPage<AiQuestionVectorVO> voPage = page.convert(item ->
                BeanUtil.copyProperties(item, AiQuestionVectorVO.class));

        List<AiQuestionVectorVO> records = voPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return voPage;
        }

        // 批量查询科目名称
        List<Long> subjectIds = records.stream().map(AiQuestionVectorVO::getSubjectId).toList();
        Map<Long, SubjectInfo> subjectMap = subjectInfoService.getSubjectInfoMap(subjectIds);

        // 年级名称
        List<Long> gradeIds = records.stream().map(AiQuestionVectorVO::getGradeId).toList();
        Map<Long, GradeInfo> gradeMap = gradeInfoService.getGradeMap(gradeIds);

        // 知识点名称
        List<Long> kpIds = records.stream().map(AiQuestionVectorVO::getKpId).toList();
        Map<Long, KnowledgePoint> kpMap = knowledgePointService.getKnowledgePointMap(kpIds);

        // 知识库名称
        List<Long> kbIds = records.stream().map(AiQuestionVectorVO::getSourceKbId).toList();
        Map<Long, KnowledgeBase> kbMap = knowledgeBaseService.getKnowledgeBaseMap(kbIds);

        List<Long> creatorIds = records.stream().map(AiQuestionVectorVO::getCreatorId).toList();
        Map<Long, SysUser> userMap = sysUserService.getUserMap(creatorIds);


        for (AiQuestionVectorVO record : records) {
            // 学科
            Optional.ofNullable(subjectMap.get(record.getSubjectId())).ifPresent(s -> record.setSubjectName(s.getSubjectName()));
            // 年级
            Optional.ofNullable(gradeMap.get(record.getGradeId())).ifPresent(g -> record.setGradeName(g.getGradeName()));
            // 知识点
            Optional.ofNullable(kpMap.get(record.getKpId())).ifPresent(k -> record.setKpName(k.getKpName()));
            if(null != record.getSourceKbId()) {
                // 来源知识库
                Optional.ofNullable(kbMap.get(record.getSourceKbId())).ifPresent(kb -> record.setSourceKbName(kb.getKbName()));
            }
            // 创建人
            Optional.ofNullable(userMap.get(record.getCreatorId())).ifPresent(u -> record.setCreatorName(u.getUserName()));
        }


        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiQuestionVector create(AiQuestionVector entity) {
        Assert.notNull(entity, "题目信息不能为空");
        Assert.notBlank(entity.getContent(), "题目内容不能为空");
        Assert.notNull(entity.getSubjectId(), "科目不能为空");

        log.info("新增出题参数:{}", JSONUtil.toJsonStr(entity));

        if(StrUtil.isBlank(entity.getQuestionNo())) {
            entity.setQuestionNo(SysConstants.generateCode(this.maxSeq()));
        }

        entity.setEmbedding(embeddingService.embed(entity.getContent()));
        if (entity.getMetadata() == null) {
            entity.setMetadata(Map.of());
        }
        if(null == entity.getCreatorId()) {
            entity.setCreatorId(UserUtil.getLoginUser().getId());
        }

        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiQuestionVector update(AiQuestionVector entity) {
        Assert.notNull(entity, "编辑题目信息不能为空");
        Assert.notBlank(entity.getContent(), "编辑题目内容不能为空");
        Assert.notNull(entity.getSubjectId(), "编辑科目不能为空");

        log.info("编辑出题参数:{}", JSONUtil.toJsonStr(entity));

        // 修改内容 → 重新生成向量
        entity.setEmbedding(embeddingService.embed(entity.getContent()));
        entity.setUpdateTime(LocalDateTime.now());
        // metadata 空值保护
        if (entity.getMetadata() == null) {
            entity.setMetadata(Map.of());
        }
        if(null == entity.getCreatorId()) {
            entity.setCreatorId(UserUtil.getLoginUser().getId());
        }

        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "id不能为空");
        return this.removeById(id);
    }
}
