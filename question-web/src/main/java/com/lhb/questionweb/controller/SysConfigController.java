package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.SysConfigQueryDTO;
import com.lhb.entity.SysConfig;
import com.lhb.service.SysConfigService;
import com.lhb.vo.SysConfigVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @GetMapping("/{id}")
    public ApiResult<SysConfig> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(sysConfigService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysConfig>> list() {
        return ApiResult.ok(sysConfigService.list());
    }

    @PostMapping("/page")
    public PageResult<SysConfigVO> page(@RequestBody SysConfigQueryDTO dto) {
        IPage<SysConfigVO> page = sysConfigService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody SysConfig entity) {
        Assert.notNull(entity, "必填参数不能为空");
        sysConfigService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody SysConfig entity) {
        Assert.notNull(entity, "必填参数不能为空");
        sysConfigService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        sysConfigService.deleteById(id);
        return ApiResult.ok();
    }
}
