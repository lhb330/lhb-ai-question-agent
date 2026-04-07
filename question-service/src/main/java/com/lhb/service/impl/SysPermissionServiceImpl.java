package com.lhb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.entity.SysPermission;
import com.lhb.mapper.SysPermissionMapper;
import com.lhb.service.SysPermissionService;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public IPage<SysPermission> pageList(long pageNum, long pageSize) {
        return this.page(new PageDTO<>(pageNum, pageSize));
    }

    @Override
    public SysPermission create(SysPermission entity) {
        this.save(entity);
        return entity;
    }

    @Override
    public SysPermission update(SysPermission entity) {
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }
}
