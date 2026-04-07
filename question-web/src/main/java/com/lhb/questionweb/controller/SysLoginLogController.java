package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.SysLoginLogDTO;
import com.lhb.entity.SysLoginLog;
import com.lhb.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/login-log")
@RequiredArgsConstructor
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    @GetMapping("/{id}")
    public ApiResult<SysLoginLog> getById(@PathVariable Long id) {
        return ApiResult.ok(sysLoginLogService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysLoginLog>> list() {
        return ApiResult.ok(sysLoginLogService.list());
    }

    @PostMapping("/page")
    public PageResult<SysLoginLog> page(@RequestBody SysLoginLogDTO dto) {
        IPage<SysLoginLog> page = sysLoginLogService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<SysLoginLog> create(@RequestBody SysLoginLog entity) {
        return ApiResult.ok(sysLoginLogService.create(entity));
    }

    @PutMapping
    public ApiResult<SysLoginLog> update(@RequestBody SysLoginLog entity) {
        return ApiResult.ok(sysLoginLogService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(sysLoginLogService.deleteById(id));
    }
}
