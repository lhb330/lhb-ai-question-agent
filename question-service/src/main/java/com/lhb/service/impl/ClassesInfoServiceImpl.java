package com.lhb.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.ClassesInfoDTO;
import com.lhb.entity.ClassesInfo;
import com.lhb.mapper.ClassesInfoMapper;
import com.lhb.service.ClassesInfoService;
import com.lhb.vo.ClassesInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClassesInfoServiceImpl extends ServiceImpl<ClassesInfoMapper, ClassesInfo> implements ClassesInfoService {

    @Override
    public IPage<ClassesInfoVO> pageList(ClassesInfoDTO dto) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClassesInfo create(ClassesInfo entity) {
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClassesInfo update(ClassesInfo entity) {
        this.updateById(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return this.removeBatchByIds(List.of(id));
    }

    @Override
    public List<ClassesInfo> getBySchoolIds(List<Long> schoolIds) {
        if(CollUtil.isEmpty(schoolIds)) {
            return List.of();
        }
        LambdaQueryWrapper<ClassesInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ClassesInfo::getSchoolId,schoolIds);
        wrapper.orderByAsc(ClassesInfo::getSchoolId);
        return this.list(wrapper);
    }

    @Override
    public List<ClassesInfo> getByGradeIds(List<Long> gradeIds) {
        if(CollUtil.isEmpty(gradeIds)) {
            return List.of();
        }
        LambdaQueryWrapper<ClassesInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ClassesInfo::getGradeId,gradeIds);
        wrapper.orderByAsc(ClassesInfo::getGradeId);
        return this.list(wrapper);
    }

    @Override
    public Map<Long, ClassesInfo> getClassMap(Collection<Long> classIds) {
        Assert.notEmpty(classIds,"班级ids不能为空");
        return this.listByIds(classIds).stream()
                .collect(Collectors.toMap(ClassesInfo::getId, c -> c));
    }
}