package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.UserQueryDTO;
import com.lhb.entity.SysUser;
import com.lhb.service.SysUserService;
import com.lhb.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("/{id}")
    public ApiResult<SysUser> getById(@PathVariable Long id) {
        return ApiResult.ok(sysUserService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysUser>> list() {
        return ApiResult.ok(sysUserService.list());
    }

    @PostMapping("/page")
    public PageResult<SysUserVO> page(@RequestBody UserQueryDTO dto) {
        IPage<SysUserVO> page = sysUserService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody SysUser entity) {
        sysUserService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody SysUser entity) {
        sysUserService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        sysUserService.deleteById(id);
        return ApiResult.ok();
    }

    @PostMapping("/reset-password")
    public ApiResult<Boolean> resetPassword(@RequestBody List<Long> userIds) {
        return ApiResult.ok(sysUserService.resetPassword(userIds));
    }
}
