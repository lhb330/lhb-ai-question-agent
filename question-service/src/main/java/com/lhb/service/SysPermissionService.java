package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.entity.SysPermission;

public interface SysPermissionService extends IService<SysPermission> {

    IPage<SysPermission> pageList(long pageNum, long pageSize);

    SysPermission create(SysPermission entity);

    SysPermission update(SysPermission entity);

    boolean deleteById(Long id);
}
