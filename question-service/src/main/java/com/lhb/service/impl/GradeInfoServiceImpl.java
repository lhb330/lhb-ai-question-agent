package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.GradeInfoDTO;
import com.lhb.entity.GradeInfo;
import com.lhb.entity.SchoolInfo;
import com.lhb.mapper.GradeInfoMapper;
import com.lhb.service.GradeInfoService;
import com.lhb.service.SchoolInfoService;
import com.lhb.vo.GradeInfoGroupVO;
import com.lhb.vo.GradeInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 年级信息 Service 实现
 * <p>包含年级分页、增删改、按学校查询、年级分组树结构</p>
 *
 * @author lhb
 * @since 2026-04
 */
@Slf4j
@Service
public class GradeInfoServiceImpl extends ServiceImpl<GradeInfoMapper, GradeInfo> implements GradeInfoService {

    @Resource
    private SchoolInfoService schoolInfoService;

    @Override
    public IPage<GradeInfoVO> pageList(GradeInfoDTO dto) {
        return null;
    }

    /**
     * 新增年级
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GradeInfo create(GradeInfo entity) {
        Assert.notNull(entity, "年级信息不能为空");
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GradeInfo update(GradeInfo entity) {
        Assert.notNull(entity, "年级信息不能为空");
        Assert.notNull(entity.getId(), "年级ID不能为空");
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "年级ID不能为空");
        return this.removeById(id);
    }

    @Override
    public List<GradeInfo> getBySchoolIds(List<Long> schoolIds) {
        Assert.notEmpty(schoolIds, "学校ID集合不能为空");
        LambdaQueryWrapper<GradeInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(GradeInfo::getSchoolId, schoolIds)
                .orderByAsc(GradeInfo::getSchoolId);
        return this.list(wrapper);
    }

    /**
     * 根据学校ID集合 → 转为 Map<学校ID, 年级列表>
     */
    @Override
    public Map<Long, List<GradeInfo>> getGradeMapList(List<Long> schoolIds) {
        if(CollUtil.isEmpty(schoolIds)) {
            return Map.of();
        }
        List<GradeInfo> gradeInfoList = this.getBySchoolIds(schoolIds);
        if (CollUtil.isEmpty(gradeInfoList)) {
            return Map.of();
        }
        return gradeInfoList.stream()
                .collect(Collectors.groupingBy(GradeInfo::getSchoolId));
    }

    /**
     * 根据年级ID集合 → 转为 Map<年级ID, 年级信息>
     */
    @Override
    public Map<Long, GradeInfo> getGradeMap(Collection<Long> gradeIds) {
        if(CollUtil.isEmpty(gradeIds)) {
            return Map.of();
        }
        List<GradeInfo> list = this.listByIds(gradeIds);
        return list.stream()
                .collect(Collectors.toMap(GradeInfo::getId, g -> g));
    }

    /**
     * 查询全部年级（带学校名称）
     */
    @Override
    public List<GradeInfoVO> getGradeInfoAll() {
        List<GradeInfo> list = this.list();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        // 转换VO
        List<GradeInfoVO> voList = BeanUtil.copyToList(list, GradeInfoVO.class);

        // 批量查询学校信息（性能优化：一次查询）
        List<Long> schoolIds = voList.stream()
                .map(GradeInfoVO::getSchoolId)
                .toList();
        Map<Long, SchoolInfo> schoolMap = schoolInfoService.getSchoolMap(schoolIds);

        // 批量赋值学校名称
        voList.forEach(vo -> {
            SchoolInfo schoolInfo = schoolMap.get(vo.getSchoolId());
            if (schoolInfo != null) {
                vo.setSchoolName(schoolInfo.getSchoolName());
            }
        });

        return voList;
    }

    /**
     * 获取【学校-年级】树形分组结构
     * 父级：学校
     * 子级：年级
     */
    @Override
    public List<GradeInfoGroupVO> getGradeInfoGroup() {
        // 1. 查询所有学校
        List<SchoolInfo> schoolList = schoolInfoService.list();
        Assert.notEmpty(schoolList, "学校数据不能为空");

        // 2. 批量查询所有年级并按学校分组（性能核心：避免双层循环）
        List<Long> schoolIds = schoolList.stream()
                .map(SchoolInfo::getId)
                .toList();
        Map<Long, List<GradeInfo>> gradeMap = this.getGradeMapList(schoolIds);

        // 3. 组装树结构
        return schoolList.stream().map(school -> {
            GradeInfoGroupVO vo = new GradeInfoGroupVO();
            vo.setId(school.getId());
            vo.setGradeName(school.getSchoolName());
            vo.setGradeCode(school.getSchoolCode());

            // 子年级
            List<GradeInfoGroupVO> children = gradeMap.getOrDefault(school.getId(), Collections.emptyList())
                    .stream()
                    .map(grade -> {
                        GradeInfoGroupVO child = new GradeInfoGroupVO();
                        child.setId(grade.getId());
                        child.setGradeName(grade.getGradeName());
                        child.setGradeCode(grade.getGradeCode());
                        child.setChildList(Collections.emptyList());
                        return child;
                    }).toList();

            vo.setChildList(children);
            return vo;
        }).sorted(Comparator.comparing(GradeInfoGroupVO::getId))
                .toList();
    }
}