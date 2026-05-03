package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.SysOperLogQueryDTO;
import com.lhb.entity.SysOperLog;
import com.lhb.vo.SysOperLogVO;

public interface SysOperLogService extends IService<SysOperLog> {

    IPage<SysOperLogVO> pageList(SysOperLogQueryDTO dto);

    SysOperLog create(SysOperLog entity);

    boolean deleteById(Long id);
}
