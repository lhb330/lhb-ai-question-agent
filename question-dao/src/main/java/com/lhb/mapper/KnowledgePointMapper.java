package com.lhb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lhb.entity.KnowledgePoint;
import org.apache.ibatis.annotations.Param;

public interface KnowledgePointMapper extends BaseMapper<KnowledgePoint> {

    /**
     * 获取知识点最大排序值 sort，无数据时返回 0
     */
    Integer getMaxSort();

    /**
     * 查询当前科目下最大知识点序号
     * @param subjectId
     * @return
     */
    Integer getMaxSeqBySubjectId(@Param("subjectId") Long subjectId);

}
