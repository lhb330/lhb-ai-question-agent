package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.SysConfigQueryDTO;
import com.lhb.entity.SysConfig;
import com.lhb.mapper.SysConfigMapper;
import com.lhb.service.SysConfigService;
import com.lhb.vo.SysConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public IPage<SysConfigVO> pageList(SysConfigQueryDTO dto) {
        log.info("系统配置查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dto.getConfigKey()), SysConfig::getConfigKey, dto.getConfigKey());
        wrapper.eq(StrUtil.isNotBlank(dto.getConfigType()), SysConfig::getConfigType, dto.getConfigType());
        wrapper.like(StrUtil.isNotBlank(dto.getConfigDesc()), SysConfig::getConfigDesc, dto.getConfigDesc());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getStatus()), SysConfig::getStatus, dto.getStatus());
        Page<SysConfig> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            SysConfigVO vo = new SysConfigVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfig create(SysConfig entity) {
        log.info("系统配置新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfig update(SysConfig entity) {
        log.info("系统配置编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("系统配置删除参数：id={}", id);
        return this.removeById(id);
    }
}
