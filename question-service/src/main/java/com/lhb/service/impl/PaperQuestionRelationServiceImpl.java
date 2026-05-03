package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.PaperQuestionRelationQueryDTO;
import com.lhb.entity.PaperQuestionRelation;
import com.lhb.mapper.PaperQuestionRelationMapper;
import com.lhb.service.PaperQuestionRelationService;
import com.lhb.vo.PaperQuestionRelationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PaperQuestionRelationServiceImpl extends ServiceImpl<PaperQuestionRelationMapper, PaperQuestionRelation> implements PaperQuestionRelationService {

    @Override
    public IPage<PaperQuestionRelationVO> pageList(PaperQuestionRelationQueryDTO dto) {
        log.info("试卷题目关联查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<PaperQuestionRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getPaperId()), PaperQuestionRelation::getPaperId, dto.getPaperId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getQuestionId()), PaperQuestionRelation::getQuestionId, dto.getQuestionId());
        wrapper.orderByAsc(PaperQuestionRelation::getQuestionSort);
        Page<PaperQuestionRelation> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            PaperQuestionRelationVO vo = new PaperQuestionRelationVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaperQuestionRelation create(PaperQuestionRelation entity) {
        log.info("试卷题目关联新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaperQuestionRelation update(PaperQuestionRelation entity) {
        log.info("试卷题目关联编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("试卷题目关联删除参数：id={}", id);
        return this.removeById(id);
    }
}
