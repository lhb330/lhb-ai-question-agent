package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.entity.SysUserPermission;
import com.lhb.service.SysUserPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user-permission")
@RequiredArgsConstructor
public class SysUserPermissionController {

    private final SysUserPermissionService sysUserPermissionService;

    @GetMapping("/{id}")
    public ApiResult<SysUserPermission> getById(@PathVariable Long id) {
        return ApiResult.ok(sysUserPermissionService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysUserPermission>> list() {
        return ApiResult.ok(sysUserPermissionService.list());
    }

    @GetMapping("/page")
    public PageResult<SysUserPermission> page(@RequestParam(defaultValue = "1") long pageNum,
                               @RequestParam(defaultValue = "10") long pageSize) {
        IPage<SysUserPermission> page = sysUserPermissionService.pageList(pageNum, pageSize);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<SysUserPermission> create(@RequestBody SysUserPermission entity) {
        return ApiResult.ok(sysUserPermissionService.create(entity));
    }

    @PutMapping
    public ApiResult<SysUserPermission> update(@RequestBody SysUserPermission entity) {
        return ApiResult.ok(sysUserPermissionService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(sysUserPermissionService.deleteById(id));
    }
}
