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
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.SubjectInfoDTO;
import com.lhb.entity.*;
import com.lhb.enums.DictTypeEnum;
import com.lhb.enums.StageCodeEnum;
import com.lhb.mapper.SubjectInfoMapper;
import com.lhb.service.SubjectInfoService;
import com.lhb.service.SysDictService;
import com.lhb.vo.SubjectGroupVO;
import com.lhb.vo.SubjectInfoVO;
import com.lhb.vo.SysUserVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectInfoServiceImpl extends ServiceImpl<SubjectInfoMapper, SubjectInfo> implements SubjectInfoService {

    @Resource
    private SysDictService sysDictService;

    @Override
    public IPage<SubjectInfoVO> pageList(SubjectInfoDTO dto) {
        Assert.notNull(dto, "查询参数不能为空");
        log.info("科目分页查询条件：{}", JSONUtil.toJsonStr(dto));

        LambdaQueryWrapper<SubjectInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(dto.getSubjectCode()),SubjectInfo::getStageCode,dto.getSubjectCode())
                .like(StrUtil.isNotBlank(dto.getSubjectName()),SubjectInfo::getSubjectName,dto.getSubjectName())
                .eq(StrUtil.isNotBlank(dto.getStageCode()),SubjectInfo::getStageCode,dto.getStageCode())
                .orderByAsc(SubjectInfo::getId);

        Page<SubjectInfo> page = this.page(dto.getPage(), queryWrapper);
        if (CollUtil.isEmpty(page.getRecords())) {
            return page.convert(item -> BeanUtil.copyProperties(item, SubjectInfoVO.class));
        }

        Map<String, SysDict> stageDictMap = sysDictService.getDictMap(DictTypeEnum.STAGE);

        return page.convert(subjectInfo -> {
            SubjectInfoVO vo = BeanUtil.copyProperties(subjectInfo, SubjectInfoVO.class);
            setSubjectAssociateNames(vo, stageDictMap);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubjectInfo create(SubjectInfo entity) {
        Assert.notNull(entity, "科目信息不能为空");
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubjectInfo update(SubjectInfo entity) {
        Assert.notNull(entity, "科目信息不能为空");
        Assert.notNull(entity.getId(), "科目ID不能为空");

        boolean updateSuccess = this.updateById(entity);
        if (!updateSuccess) {
            log.warn("科目信息更新失败，ID：{}", entity.getId());
        }

        return baseMapper.selectById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "科目ID不能为空");

        boolean removeSuccess = this.removeById(id);
        if (!removeSuccess) {
            log.warn("科目信息删除失败，ID：{}", id);
        }

        return this.removeById(id);
    }

    @Override
    public List<SubjectInfo> getByStageCode(String stageCode) {
        Assert.notEmpty(stageCode,"stageCode学段编码不能为空");
        return this.getByStageCode(List.of(stageCode));
    }

    @Override
    public List<SubjectInfo> getByStageCode(List<String> stageCodeList) {
        Assert.notEmpty(stageCodeList,"stageCodes学段编码不能为空");
        LambdaQueryWrapper<SubjectInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SubjectInfo::getStageCode,stageCodeList);
        if(stageCodeList.size() == 1) {
            wrapper.orderByAsc(SubjectInfo::getSort);
        }
        return this.list(wrapper);
    }

    @Override
    public List<SubjectGroupVO> getSubjectGroupList() {
        List<SubjectInfo> stageCodeList = this.getByStageCode(List.of(
                StageCodeEnum.PRIMARY.getValue(),
                StageCodeEnum.JUNIOR.getValue(),
                StageCodeEnum.SENIOR.getValue()));

        List<SubjectInfo> primaryList = stageCodeList.stream()
                .filter(s -> StrUtil.equals(StageCodeEnum.PRIMARY.getValue(),s.getStageCode()))
                .sorted(Comparator.comparingInt(SubjectInfo::getSort))
                .toList();

        List<SubjectInfo> juniorList = stageCodeList.stream()
                .filter(s -> StrUtil.equals(StageCodeEnum.JUNIOR.getValue(),s.getStageCode()))
                .sorted(Comparator.comparingInt(SubjectInfo::getSort))
                .toList();

        List<SubjectInfo> seniorList = stageCodeList.stream()
                .filter(s -> StrUtil.equals(StageCodeEnum.SENIOR.getValue(),s.getStageCode()))
                .sorted(Comparator.comparingInt(SubjectInfo::getSort))
                .toList();

        List<SubjectGroupVO> result = new LinkedList<>();
        // ====================== 小学父节点
        SubjectGroupVO primaryParent = new SubjectGroupVO();
        primaryParent.setId(0L);               // 父级ID统一为0
        primaryParent.setSubjectName(StageCodeEnum.PRIMARY.getMsg());
        primaryParent.setParentId(-1L);
        primaryParent.setParentSubjectCode(StageCodeEnum.PRIMARY.getValue());
        primaryParent.setStageCode(StageCodeEnum.PRIMARY.getValue());
        primaryParent.setStageName(StageCodeEnum.PRIMARY.getMsg());
        primaryParent.setSort(1);
        // 子科目
        List<SubjectGroupVO> primaryChildren = primaryList.stream().map(s -> {
            SubjectGroupVO vo = new SubjectGroupVO();
            vo.setId(s.getId());
            vo.setSubjectName(s.getSubjectName());
            vo.setParentId(0L);
            vo.setParentSubjectCode(s.getSubjectCode());
            vo.setStageCode(s.getStageCode());
            vo.setStageName(StageCodeEnum.PRIMARY.getMsg());
            vo.setSort(s.getSort());
            vo.setChildList(Collections.emptyList());
            return vo;
        }).toList();
        primaryParent.setChildList(primaryChildren);
        result.add(primaryParent);

        // ====================== 初中父节点
        SubjectGroupVO juniorParent = new SubjectGroupVO();
        juniorParent.setId(0L);
        juniorParent.setSubjectName(StageCodeEnum.JUNIOR.getMsg());
        juniorParent.setParentId(-1L);
        juniorParent.setParentSubjectCode(StageCodeEnum.JUNIOR.getValue());
        juniorParent.setStageCode(StageCodeEnum.JUNIOR.getValue());
        juniorParent.setStageName(StageCodeEnum.JUNIOR.getMsg());
        juniorParent.setSort(2);
        // 子科目
        List<SubjectGroupVO> juniorChildren = juniorList.stream().map(s -> {
            SubjectGroupVO vo = new SubjectGroupVO();
            vo.setId(s.getId());
            vo.setSubjectName(s.getSubjectName());
            vo.setParentId(0L);
            vo.setParentSubjectCode(s.getSubjectCode());
            vo.setStageCode(s.getStageCode());
            vo.setStageName(StageCodeEnum.JUNIOR.getMsg());
            vo.setSort(s.getSort());
            vo.setChildList(Collections.emptyList());
            return vo;
        }).toList();
        juniorParent.setChildList(juniorChildren);
        result.add(juniorParent);

        // ====================== 高中父节点
        SubjectGroupVO seniorParent = new SubjectGroupVO();
        seniorParent.setId(0L);
        seniorParent.setSubjectName(StageCodeEnum.SENIOR.getMsg());
        seniorParent.setParentId(-1L);
        seniorParent.setParentSubjectCode(StageCodeEnum.SENIOR.getValue());
        seniorParent.setStageCode(StageCodeEnum.SENIOR.getValue());
        seniorParent.setStageName(StageCodeEnum.SENIOR.getMsg());
        seniorParent.setSort(3);
        // 子科目
        List<SubjectGroupVO> seniorChildren = seniorList.stream().map(s -> {
            SubjectGroupVO vo = new SubjectGroupVO();
            vo.setId(s.getId());
            vo.setSubjectName(s.getSubjectName());
            vo.setParentId(0L);
            vo.setParentSubjectCode(s.getSubjectCode());
            vo.setStageCode(s.getStageCode());
            vo.setStageName(StageCodeEnum.SENIOR.getMsg());
            vo.setSort(s.getSort());
            vo.setChildList(Collections.emptyList());
            return vo;
        }).toList();
        seniorParent.setChildList(seniorChildren);
        result.add(seniorParent);

        return result;
    }

    @Override
    public Map<Long, SubjectInfo> getSubjectInfoMap(List<Long> ids) {
        if(CollUtil.isEmpty(ids)) {
            return Map.of();
        }
        List<SubjectInfo> list = this.listByIds(ids);
        if(CollUtil.isEmpty(list)) {
            return Map.of();
        }
        return list.stream().collect(Collectors.toMap(SubjectInfo::getId, s -> s));
    }

    @Override
    public SubjectInfoVO getById(Long id) {
        // 空值直接返回空VO
        if (id == null || id <= 0) {
            return new SubjectInfoVO();
        }
        // 查询
        Map<Long, SubjectInfo> map = this.getSubjectInfoMap(List.of(id));
        SubjectInfo info = map.get(id);
        // 没查到 → 返回空VO，不返回null
        if (info == null) {
            return new SubjectInfoVO();
        }
        // 拷贝属性返回
        return BeanUtil.copyProperties(info, SubjectInfoVO.class);
    }

    /**
     * 给用户VO设置关联名称（解耦核心逻辑）
     */
    private void setSubjectAssociateNames(SubjectInfoVO vo, Map<String, SysDict> stageDictMap) {
        // 学段名称
        SysDict stageDict = stageDictMap.get(vo.getStageCode());
        if (null != stageDict) {
            vo.setStageName(stageDict.getDictName());
        }
    }
}
