package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.DictQueryDTO;
import com.lhb.entity.SysDict;
import com.lhb.enums.DictTypeEnum;
import com.lhb.enums.StatusEnum;
import com.lhb.mapper.SysDictMapper;
import com.lhb.service.SysDictService;
import com.lhb.vo.SysDictVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public IPage<SysDictVO> pageList(DictQueryDTO dto) {
        log.info("字典查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StrUtil.isNotBlank(dto.getDictType()),SysDict::getDictType,dto.getDictType());
        wrapper.eq(StrUtil.isNotBlank(dto.getDictCode()),SysDict::getDictCode,dto.getDictCode());
        wrapper.like(StrUtil.isNotBlank(dto.getDictName()),SysDict::getDictName,dto.getDictName());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getStatus()),SysDict::getStatus,dto.getStatus());
        Page<SysDict> page = this.page(dto.getPage(), wrapper);
        return page.convert(dict -> {
            SysDictVO vo = new SysDictVO();
            BeanUtil.copyProperties(dict, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDict create(SysDict entity) {
        log.info("字典新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysDict update(SysDict entity) {
        log.info("字典编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("字典删除参数：id={}", id);
        return this.removeById(id);
    }

    @Override
    public List<SysDict> getByDictType(DictTypeEnum dictTypeEnum) {
        return this.getByDictType(dictTypeEnum.getDictType());
    }

    @Override
    public List<SysDict> getByDictType(String dictType) {
        Assert.notBlank(dictType,"dictType参数不能为空");
        return this.getByDictType(List.of(dictType));
    }

    @Override
    public List<SysDict> getByDictType(List<String> dictTypeList) {
        Assert.notEmpty(dictTypeList, "dictType集合参数不能为空");

        // 去重 + 过滤null（字符串安全去重）
        List<String> distinctDictTypeList = dictTypeList.stream()
                .filter(StrUtil::isNotBlank)
                .distinct()
                .toList();

        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysDict::getDictType, distinctDictTypeList);
        //wrapper.eq(SysDict::getStatus, StatusEnum.ENABLED.getCode());

        // 只有 1个字典类型时才排序（多个类型sort不通用，不排序）
        if (distinctDictTypeList.size() == 1) {
            wrapper.orderByAsc(SysDict::getSort);
        }

        List<SysDict> list = this.list(wrapper);
        return CollUtil.isEmpty(list) ? Collections.emptyList() : list;
    }

    @Override
    public Map<String, SysDict> getDictMap(DictTypeEnum dictTypeEnum) {
        return this.getDictMap(List.of(dictTypeEnum.getDictType()));
    }

    @Override
    public Map<String, SysDict> getDictMap(List<String> dictType) {
        if (CollUtil.isEmpty(dictType)) {
            return Collections.emptyMap();
        }
        return this.getByDictType(dictType).stream()
                .collect(Collectors.toMap(SysDict::getDictCode, d -> d));
    }
}
