package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.entity.SysUserPermission;

public interface SysUserPermissionService extends IService<SysUserPermission> {

    IPage<SysUserPermission> pageList(long pageNum, long pageSize);

    SysUserPermission create(SysUserPermission entity);

    SysUserPermission update(SysUserPermission entity);

    boolean deleteById(Long id);
}
