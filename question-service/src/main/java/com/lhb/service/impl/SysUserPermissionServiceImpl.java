package com.lhb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.entity.SysUserPermission;
import com.lhb.mapper.SysUserPermissionMapper;
import com.lhb.service.SysUserPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysUserPermissionServiceImpl extends ServiceImpl<SysUserPermissionMapper, SysUserPermission> implements SysUserPermissionService {

    @Override
    public IPage<SysUserPermission> pageList(long pageNum, long pageSize) {
        return this.page(new PageDTO<>(pageNum, pageSize));
    }

    @Override
    public SysUserPermission create(SysUserPermission entity) {
        this.save(entity);
        return entity;
    }

    @Override
    public SysUserPermission update(SysUserPermission entity) {
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }
}
