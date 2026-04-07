package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.SubjectInfoDTO;
import com.lhb.entity.SubjectInfo;
import com.lhb.vo.SubjectGroupVO;
import com.lhb.vo.SubjectInfoVO;

import java.util.List;
import java.util.Map;

public interface SubjectInfoService extends IService<SubjectInfo> {

    IPage<SubjectInfoVO> pageList(SubjectInfoDTO dto);

    SubjectInfo create(SubjectInfo entity);

    SubjectInfo update(SubjectInfo entity);

    boolean deleteById(Long id);

    List<SubjectInfo> getByStageCode(String stageCode);

    List<SubjectInfo> getByStageCode(List<String> stageCodeList);

    List<SubjectGroupVO> getSubjectGroupList();

    Map<Long,SubjectInfo> getSubjectInfoMap(List<Long> ids);

    SubjectInfoVO getById(Long id);
}
