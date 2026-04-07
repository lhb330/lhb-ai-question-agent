package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.entity.SysPermission;
import com.lhb.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @GetMapping("/{id}")
    public ApiResult<SysPermission> getById(@PathVariable Long id) {
        return ApiResult.ok(sysPermissionService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysPermission>> list() {
        return ApiResult.ok(sysPermissionService.list());
    }

    @GetMapping("/page")
    public PageResult<SysPermission> page(@RequestParam(defaultValue = "1") long pageNum,
                               @RequestParam(defaultValue = "10") long pageSize) {
        IPage<SysPermission> page = sysPermissionService.pageList(pageNum, pageSize);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<SysPermission> create(@RequestBody SysPermission entity) {
        return ApiResult.ok(sysPermissionService.create(entity));
    }

    @PutMapping
    public ApiResult<SysPermission> update(@RequestBody SysPermission entity) {
        return ApiResult.ok(sysPermissionService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(sysPermissionService.deleteById(id));
    }
}
