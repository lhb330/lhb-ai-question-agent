package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.KnowledgeBaseChunkQueryDTO;
import com.lhb.entity.KnowledgeBaseChunkVector;
import com.lhb.mapper.KnowledgeBaseChunkVectorMapper;
import com.lhb.service.KnowledgeBaseChunkVectorService;
import com.lhb.vo.KnowledgeBaseChunkVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class KnowledgeBaseChunkVectorServiceImpl extends ServiceImpl<KnowledgeBaseChunkVectorMapper, KnowledgeBaseChunkVector> implements KnowledgeBaseChunkVectorService {

    @Override
    public IPage<KnowledgeBaseChunkVO> pageList(KnowledgeBaseChunkQueryDTO dto) {
        log.info("知识库分块查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<KnowledgeBaseChunkVector> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getKbId()), KnowledgeBaseChunkVector::getKbId, dto.getKbId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getChunkNo()), KnowledgeBaseChunkVector::getChunkNo, dto.getChunkNo());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getStatus()), KnowledgeBaseChunkVector::getStatus, dto.getStatus());
        wrapper.orderByAsc(KnowledgeBaseChunkVector::getChunkNo);
        Page<KnowledgeBaseChunkVector> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            KnowledgeBaseChunkVO vo = new KnowledgeBaseChunkVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeBaseChunkVector create(KnowledgeBaseChunkVector entity) {
        log.info("知识库分块新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgeBaseChunkVector update(KnowledgeBaseChunkVector entity) {
        log.info("知识库分块编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("知识库分块删除参数：id={}", id);
        return this.removeById(id);
    }
}
