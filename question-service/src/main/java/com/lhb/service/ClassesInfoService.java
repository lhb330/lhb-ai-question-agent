package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.ClassesInfoDTO;
import com.lhb.entity.ClassesInfo;
import com.lhb.vo.ClassesInfoVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface ClassesInfoService extends IService<ClassesInfo> {

    IPage<ClassesInfoVO> pageList(ClassesInfoDTO dto);

    ClassesInfo create(ClassesInfo entity);

    ClassesInfo update(ClassesInfo entity);

    boolean deleteById(Long id);

    List<ClassesInfo> getBySchoolIds(List<Long> schoolIds);

    List<ClassesInfo> getByGradeIds(List<Long> gradeIds);

    Map<Long, ClassesInfo> getClassMap(Collection<Long> classIds);

}