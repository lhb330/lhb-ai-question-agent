package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.PaperInfoQueryDTO;
import com.lhb.entity.PaperInfo;
import com.lhb.vo.PaperInfoVO;

public interface PaperInfoService extends IService<PaperInfo> {

    IPage<PaperInfoVO> pageList(PaperInfoQueryDTO dto);

    PaperInfo create(PaperInfo entity);

    PaperInfo update(PaperInfo entity);

    boolean deleteById(Long id);
}
