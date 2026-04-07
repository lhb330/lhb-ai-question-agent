package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.KnowledgePointDTO;
import com.lhb.entity.KnowledgePoint;
import com.lhb.vo.KnowledgePointVO;

import java.util.List;
import java.util.Map;

public interface KnowledgePointService extends IService<KnowledgePoint> {

    List<KnowledgePointVO> getList();

    IPage<KnowledgePointVO> pageList(KnowledgePointDTO dto);

    KnowledgePoint create(KnowledgePoint entity);

    KnowledgePoint update(KnowledgePoint entity);

    boolean deleteById(Long id);

    /**
     * 获取知识点最大排序值 sort，无数据时返回 0
     */
    Integer getMaxSort();

    /**
     * 查询当前科目下最大知识点序号
     * @param subjectId
     * @return
     */
    Integer getMaxSeqBySubjectId(Long subjectId);

    List<KnowledgePointVO> getKpParent();

    List<KnowledgePointVO> getKpChild(List<Long> parentKpIds);

    Map<Long,KnowledgePoint> getKnowledgePointMap(List<Long> ids);

    KnowledgePointVO getById(Long id);
}
