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
import com.lhb.dto.SchoolInfoDTO;
import com.lhb.entity.SchoolInfo;
import com.lhb.enums.SchoolTypeEnum;
import com.lhb.enums.StatusEnum;
import com.lhb.mapper.SchoolInfoMapper;
import com.lhb.service.SchoolInfoService;
import com.lhb.utils.ThrowUtils;
import com.lhb.vo.SchoolInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SchoolInfoServiceImpl extends ServiceImpl<SchoolInfoMapper, SchoolInfo> implements SchoolInfoService {

    /**
     * 学校信息分页查询
     */
    @Override
    public IPage<SchoolInfoVO> pageList(SchoolInfoDTO dto) {
        // 参数校验
        Assert.notNull(dto, "查询参数不能为空");
        log.info("学校信息分页查询参数：{}", JSONUtil.toJsonStr(dto));

        LambdaQueryWrapper<SchoolInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getSchoolCode()),SchoolInfo::getSchoolCode,dto.getSchoolCode())
                .like(StrUtil.isNotBlank(dto.getSchoolName()),SchoolInfo::getSchoolName,dto.getSchoolName())
                .eq(ObjUtil.isNotNull(dto.getSchoolType()),SchoolInfo::getSchoolType,dto.getSchoolType())
                .eq(ObjUtil.isNotNull(dto.getStatus()),SchoolInfo::getStatus,dto.getStatus())
                .orderByDesc(SchoolInfo::getUpdateTime);

        // 分页查询 + 自动转换VO
        Page<SchoolInfo> page = this.page(dto.getPage(), wrapper);
        return page.convert(item -> BeanUtil.copyProperties(item, SchoolInfoVO.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SchoolInfo create(SchoolInfo entity) {
        // 入参校验
        Assert.notNull(entity, "学校信息不能为空");
        Assert.isTrue(StrUtil.isNotBlank(entity.getSchoolName()), "学校名称不能为空");

        // 默认值填充
        entity.setSchoolType(ObjUtil.defaultIfNull(entity.getSchoolType(), SchoolTypeEnum.HEAD.getCode()));
        entity.setParentSchoolId(ObjUtil.defaultIfNull(entity.getParentSchoolId(), 0L));
        entity.setStatus(ObjUtil.defaultIfNull(entity.getStatus(), StatusEnum.ENABLED.getCode()));

        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());

        // 保存
        boolean saveSuccess = this.save(entity);
        if (!saveSuccess) {
            log.error("学校信息新增失败：{}", JSONUtil.toJsonStr(entity));
            ThrowUtils.throwBusiness("学校信息新增失败");
        }

        return entity;
    }

    /**
     * 修改学校信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SchoolInfo update(SchoolInfo entity) {
        Assert.notNull(entity, "学校信息不能为空");
        Assert.notNull(entity.getId(), "学校ID不能为空");

        // 更新时间
        entity.setUpdateTime(LocalDateTime.now());

        // 执行更新
        boolean updateSuccess = this.updateById(entity);
        if (!updateSuccess) {
            log.error("学校信息更新失败，ID不存在：{}", entity.getId());
            ThrowUtils.throwBusiness("学校信息更新失败");
        }
        return this.getById(entity.getId());
    }

    /**
     * 删除学校信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "学校ID不能为空");
        boolean removeSuccess = this.removeById(id);
        if (!removeSuccess) {
            log.error("学校信息删除失败，ID不存在：{}", id);
            ThrowUtils.throwBusiness("学校信息删除失败，ID不存在："+ id);
        }
        return true;
    }

    /**
     * 查询所有顶级学校（parentSchoolId=0）
     */
    @Override
    public List<SchoolInfoVO> getByParentSchool() {
        LambdaQueryWrapper<SchoolInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SchoolInfo::getParentSchoolId,0L);

        List<SchoolInfo> list = this.list(wrapper);
        return BeanUtil.copyToList(list, SchoolInfoVO.class);
    }

    /**
     * 根据父ID查询子学校（单ID）
     */
    @Override
    public List<SchoolInfoVO> getChildByParentSchoolId(Long parentSchoolId) {
        Assert.notNull(parentSchoolId, "父级学校ID不能为空");
        return getChildByParentSchoolId(List.of(parentSchoolId));
    }

    /**
     * 根据父ID集合查询子学校【parentSchoolIds】
     */
    @Override
    public List<SchoolInfoVO> getChildByParentSchoolId(List<Long> parentSchoolIds) {
        Assert.notEmpty(parentSchoolIds,"父级学校id不能为空");
        LambdaQueryWrapper<SchoolInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SchoolInfo::getId,parentSchoolIds);

        List<SchoolInfo> list = this.list(wrapper);
        return BeanUtil.copyToList(list, SchoolInfoVO.class);
    }

    @Override
    public Map<Long, SchoolInfo> getSchoolMap(Collection<Long> schoolIds) {
        if(CollUtil.isEmpty(schoolIds)) {
            return Map.of();
        }
        List<SchoolInfo> list = listByIds(schoolIds);
        if(CollUtil.isEmpty(list)) {
            return Map.of();
        }
        return list.stream()
                .collect(Collectors.toMap(SchoolInfo::getId, s -> s));
    }
}
