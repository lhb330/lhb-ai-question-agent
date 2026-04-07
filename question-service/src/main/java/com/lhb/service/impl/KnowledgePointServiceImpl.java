package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.constants.SysConstants;
import com.lhb.dto.KnowledgePointDTO;
import com.lhb.entity.KnowledgePoint;
import com.lhb.entity.SubjectInfo;
import com.lhb.enums.StatusEnum;
import com.lhb.mapper.KnowledgePointMapper;
import com.lhb.service.KnowledgePointService;
import com.lhb.service.SubjectInfoService;
import com.lhb.vo.KnowledgePointVO;
import com.lhb.vo.SubjectInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KnowledgePointServiceImpl extends ServiceImpl<KnowledgePointMapper, KnowledgePoint> implements KnowledgePointService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Override
    public List<KnowledgePointVO> getList() {
        List<KnowledgePoint> list = this.list();
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<KnowledgePointVO> knowledgePointVOS = BeanUtil.copyToList(list, KnowledgePointVO.class);

        // 调用公共方法填充信息
        fillKnowledgePointInfo(knowledgePointVOS);

        return knowledgePointVOS;
    }

    @Override
    public IPage<KnowledgePointVO> pageList(KnowledgePointDTO dto) {
        Assert.notNull(dto, "知识点查询参数不能为空");
        log.info("知识点查询参数：{}", JSONUtil.toJsonStr(dto));

        LambdaQueryWrapper<KnowledgePoint> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(dto.getKpName()), KnowledgePoint::getKpName,dto.getKpName())
                .eq(dto.getSubjectId() != null,KnowledgePoint::getSubjectId,dto.getSubjectId())
                .eq(StrUtil.isNotBlank(dto.getStageCode()),KnowledgePoint::getStageCode,dto.getStageCode())
                .eq(dto.getStatus() != null,KnowledgePoint::getStatus,dto.getStatus())
                .orderByDesc(KnowledgePoint::getUpdateTime);

        Page<KnowledgePoint> page = this.page(dto.getPage(), wrapper);

        IPage<KnowledgePointVO> voPage = page.convert(item -> BeanUtil.copyProperties(item, KnowledgePointVO.class));

        List<KnowledgePointVO> records = voPage.getRecords();
        if (CollUtil.isEmpty(records)) {
            return voPage;
        }

        // 调用公共方法填充信息
        fillKnowledgePointInfo(records);

        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgePoint create(KnowledgePoint entity) {
        Assert.notNull(entity, "知识点参数不能为空");
        log.info("知识点新增参数：{}", JSONUtil.toJsonStr(entity));

        if(StrUtil.isBlank(entity.getKpCode())) {
            Integer maxSeq = this.getMaxSeqBySubjectId(entity.getSubjectId());
            String kpCode = SysConstants.generateKpCode(entity.getSubjectId(), maxSeq + 1);
            entity.setKpCode(kpCode);
        }

        if(null == entity.getParentKpId()) {
            entity.setParentKpId(0L);
        }

        if(null == entity.getStatus()) {
            entity.setStatus(StatusEnum.ENABLED.getCode());
        }

        // 找到最大的排序值
        Integer maxSort = this.getMaxSort();
        entity.setSort(maxSort + 1);

        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public KnowledgePoint update(KnowledgePoint entity) {
        Assert.notNull(entity, "知识点参数不能为空");
        Assert.notNull(entity.getId(), "知识点ID不能为空");

        if(null == entity.getParentKpId()) {
            entity.setParentKpId(0L);
        }

        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);

        return super.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        Assert.notNull(id, "知识点ID不能为空");
        return this.removeById(id);
    }

    @Override
    public Integer getMaxSort() {
        return baseMapper.getMaxSort();
    }

    @Override
    public Integer getMaxSeqBySubjectId(Long subjectId) {
        Assert.notNull(subjectId, "知识点科目ID不能为空");
        return this.baseMapper.getMaxSeqBySubjectId(subjectId);
    }

    @Override
    public List<KnowledgePointVO> getKpParent() {
        LambdaQueryWrapper<KnowledgePoint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowledgePoint::getParentKpId,0);

        List<KnowledgePoint> list = this.list(wrapper);

        return CollUtil.isEmpty(list) ? List.of()
                : BeanUtil.copyToList(list,KnowledgePointVO.class);
    }

    @Override
    public List<KnowledgePointVO> getKpChild(List<Long> parentKpIds) {
        Assert.notEmpty(parentKpIds, "父级知识点ID不能为空");

        LambdaQueryWrapper<KnowledgePoint> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(KnowledgePoint::getId,parentKpIds);

        List<KnowledgePoint> list = this.list(wrapper);

        return CollUtil.isEmpty(list) ? List.of()
                : BeanUtil.copyToList(list,KnowledgePointVO.class);
    }

    @Override
    public Map<Long, KnowledgePoint> getKnowledgePointMap(List<Long> ids) {
        if(CollUtil.isEmpty(ids)) {
            return Map.of();
        }
        List<KnowledgePoint> list = this.listByIds(ids);
        if(CollUtil.isEmpty(list)) {
            return Map.of();
        }
        return list.stream()
                .collect(Collectors.toMap(KnowledgePoint::getId, s -> s));
    }

    @Override
    public KnowledgePointVO getById(Long id) {
        // 空值直接返回空VO
        if (id == null || id <= 0) {
            return null;
        }
        // 查询
        Map<Long, KnowledgePoint> map = this.getKnowledgePointMap(List.of(id));
        KnowledgePoint info = map.get(id);
        KnowledgePointVO vo = BeanUtil.copyProperties(info,KnowledgePointVO.class );
        fillKnowledgePointInfo(List.of(vo));
        return vo;
    }

    /**
     * 公共方法：给知识点VO列表填充 科目名称、父知识点名称
     */
    private void fillKnowledgePointInfo(List<KnowledgePointVO> voList) {
        if (CollUtil.isEmpty(voList)) {
            return;
        }

        // 1. 批量查询科目
        List<Long> subjectIds = voList.stream().map(KnowledgePointVO::getSubjectId).toList();
        Map<Long, SubjectInfo> subjectMap = subjectInfoService.getSubjectInfoMap(subjectIds);

        // 2. 批量查询父知识点
        List<Long> parentKpIds = voList.stream()
                .map(KnowledgePointVO::getParentKpId)
                .filter(parentId -> !parentId.equals(0L))
                .toList();
        Map<Long, KnowledgePoint> knowledgePointMap = CollUtil.isEmpty(parentKpIds)
                ? Collections.emptyMap()
                : this.getKnowledgePointMap(parentKpIds);

        // 3. 赋值
        voList.forEach(record -> {
            // 科目名称
            SubjectInfo subjectInfo = subjectMap.get(record.getSubjectId());
            if (subjectInfo != null) {
                record.setSubjectName(subjectInfo.getSubjectName());
            }

            // 父知识点处理
            if (record.getParentKpId().equals(0L)) {
                record.setParentKpId(null);
            } else {
                KnowledgePoint parentKp = knowledgePointMap.get(record.getParentKpId());
                if (parentKp != null) {
                    record.setParentKpName(parentKp.getKpName());
                }
            }
        });
    }
}
