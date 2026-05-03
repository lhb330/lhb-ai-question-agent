package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.SysConfigQueryDTO;
import com.lhb.entity.SysConfig;
import com.lhb.vo.SysConfigVO;

public interface SysConfigService extends IService<SysConfig> {

    IPage<SysConfigVO> pageList(SysConfigQueryDTO dto);

    SysConfig create(SysConfig entity);

    SysConfig update(SysConfig entity);

    boolean deleteById(Long id);
}
