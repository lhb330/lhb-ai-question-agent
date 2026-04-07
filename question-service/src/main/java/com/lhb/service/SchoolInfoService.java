package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.SchoolInfoDTO;
import com.lhb.entity.SchoolInfo;
import com.lhb.vo.SchoolInfoVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface SchoolInfoService extends IService<SchoolInfo> {

    IPage<SchoolInfoVO> pageList(SchoolInfoDTO dto);

    SchoolInfo create(SchoolInfo entity);

    SchoolInfo update(SchoolInfo entity);

    boolean deleteById(Long id);

    List<SchoolInfoVO> getByParentSchool();

    List<SchoolInfoVO> getChildByParentSchoolId(Long parentSchoolId);

    List<SchoolInfoVO> getChildByParentSchoolId(List<Long> parentSchoolIds);

    /**
     * 批量查询学校 => Map
     */
    Map<Long, SchoolInfo> getSchoolMap(Collection<Long> schoolIds);
}
