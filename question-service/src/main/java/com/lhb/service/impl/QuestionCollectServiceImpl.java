package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.QuestionCollectQueryDTO;
import com.lhb.entity.QuestionCollect;
import com.lhb.mapper.QuestionCollectMapper;
import com.lhb.service.QuestionCollectService;
import com.lhb.vo.QuestionCollectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class QuestionCollectServiceImpl extends ServiceImpl<QuestionCollectMapper, QuestionCollect> implements QuestionCollectService {

    @Override
    public IPage<QuestionCollectVO> pageList(QuestionCollectQueryDTO dto) {
        log.info("题目收藏查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<QuestionCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getUserId()), QuestionCollect::getUserId, dto.getUserId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getQuestionId()), QuestionCollect::getQuestionId, dto.getQuestionId());
        wrapper.orderByDesc(QuestionCollect::getCreateTime);
        Page<QuestionCollect> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            QuestionCollectVO vo = new QuestionCollectVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionCollect create(QuestionCollect entity) {
        log.info("题目收藏新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuestionCollect update(QuestionCollect entity) {
        log.info("题目收藏编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("题目收藏删除参数：id={}", id);
        return this.removeById(id);
    }
}
