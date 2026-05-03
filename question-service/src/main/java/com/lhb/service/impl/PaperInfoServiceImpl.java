package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.PaperInfoQueryDTO;
import com.lhb.entity.PaperInfo;
import com.lhb.mapper.PaperInfoMapper;
import com.lhb.service.PaperInfoService;
import com.lhb.vo.PaperInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PaperInfoServiceImpl extends ServiceImpl<PaperInfoMapper, PaperInfo> implements PaperInfoService {

    @Override
    public IPage<PaperInfoVO> pageList(PaperInfoQueryDTO dto) {
        log.info("试卷查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<PaperInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dto.getPaperNo()), PaperInfo::getPaperNo, dto.getPaperNo());
        wrapper.like(StrUtil.isNotBlank(dto.getPaperName()), PaperInfo::getPaperName, dto.getPaperName());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getUserId()), PaperInfo::getUserId, dto.getUserId());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getSubjectId()), PaperInfo::getSubjectId, dto.getSubjectId());
        wrapper.eq(StrUtil.isNotBlank(dto.getStageCode()), PaperInfo::getStageCode, dto.getStageCode());
        wrapper.eq(StrUtil.isNotBlank(dto.getGradeCode()), PaperInfo::getGradeCode, dto.getGradeCode());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getStatus()), PaperInfo::getStatus, dto.getStatus());
        wrapper.orderByDesc(PaperInfo::getCreateTime);
        Page<PaperInfo> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            PaperInfoVO vo = new PaperInfoVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaperInfo create(PaperInfo entity) {
        log.info("试卷新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaperInfo update(PaperInfo entity) {
        log.info("试卷编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("试卷删除参数：id={}", id);
        return this.removeById(id);
    }
}
