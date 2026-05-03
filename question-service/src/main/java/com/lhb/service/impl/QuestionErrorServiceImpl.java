package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.QuestionErrorQueryDTO;
import com.lhb.entity.QuestionError;
import com.lhb.mapper.QuestionErrorMapper;
import com.lhb.service.QuestionErrorService;
import com.lhb.vo.QuestionErrorVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class QuestionErrorServiceImpl extends ServiceImpl<QuestionErrorMapper, QuestionError> implements QuestionErrorService {

    @Override
    public IPage<QuestionErrorVO> pageList(QuestionErrorQueryDTO dto) {
        log.info("错题查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<QuestionError> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getUserId()), QuestionError::getUserId, dto.getUserId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getQuestionId()), QuestionError::getQuestionId, dto.getQuestionId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getReviewStatus()), QuestionError::getReviewStatus, dto.getReviewStatus());
        wrapper.orderByDesc(QuestionError::getErrorTime);
        Page<QuestionError> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            QuestionErrorVO vo = new QuestionErrorVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionError create(QuestionError entity) {
        log.info("错题新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionError update(QuestionError entity) {
        log.info("错题编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("错题删除参数：id={}", id);
        return this.removeById(id);
    }
}
