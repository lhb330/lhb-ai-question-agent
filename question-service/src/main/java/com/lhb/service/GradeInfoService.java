package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.GradeInfoDTO;
import com.lhb.entity.GradeInfo;
import com.lhb.vo.GradeInfoGroupVO;
import com.lhb.vo.GradeInfoVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GradeInfoService extends IService<GradeInfo> {

    IPage<GradeInfoVO> pageList(GradeInfoDTO dto);

    GradeInfo create(GradeInfo entity);

    GradeInfo update(GradeInfo entity);

    boolean deleteById(Long id);

    List<GradeInfo> getBySchoolIds(List<Long> schoolIds);

    Map<Long, List<GradeInfo>> getGradeMapList(List<Long> schoolIds);

    /**
     * 批量查询年级 => Map
     */
    Map<Long, GradeInfo> getGradeMap(Collection<Long> gradeIds);

    List<GradeInfoVO> getGradeInfoAll();


    List<GradeInfoGroupVO> getGradeInfoGroup();



}