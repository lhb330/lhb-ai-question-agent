package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.SysOperLogQueryDTO;
import com.lhb.entity.SysOperLog;
import com.lhb.service.SysOperLogService;
import com.lhb.vo.SysOperLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/operLog")
@RequiredArgsConstructor
public class SysOperLogController {

    private final SysOperLogService sysOperLogService;

    @GetMapping("/{id}")
    public ApiResult<SysOperLog> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(sysOperLogService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SysOperLog>> list() {
        return ApiResult.ok(sysOperLogService.list());
    }

    @PostMapping("/page")
    public PageResult<SysOperLogVO> page(@RequestBody SysOperLogQueryDTO dto) {
        IPage<SysOperLogVO> page = sysOperLogService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        sysOperLogService.deleteById(id);
        return ApiResult.ok();
    }
}
