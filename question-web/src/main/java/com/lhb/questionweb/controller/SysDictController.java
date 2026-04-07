package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.DictQueryDTO;
import com.lhb.entity.SysDict;
import com.lhb.service.SysDictService;
import com.lhb.vo.SysDictVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/dict")
@RequiredArgsConstructor
public class SysDictController {

    private final SysDictService sysDictService;

    @GetMapping("/{id}")
    public ApiResult<SysDict> getById(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        return ApiResult.ok(sysDictService.getById(id));
    }

    @GetMapping("/dictType/{dictType}")
    public ApiResult<List<SysDict>> fetchDictItems(@PathVariable String dictType) {
        Assert.notEmpty(dictType,"dictType参数为空");
        List<SysDict> dictList = this.sysDictService.getByDictType(dictType);
        return ApiResult.ok(dictList);
    }

    @GetMapping("/list")
    public ApiResult<List<SysDict>> list() {
        return ApiResult.ok(sysDictService.list());
    }

    @PostMapping("/page")
    public PageResult<SysDictVO> page(@RequestBody DictQueryDTO dictQueryDTO) {
        IPage<SysDictVO> page = sysDictService.pageList(dictQueryDTO);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody SysDict entity) {
        Assert.notNull(entity,"必填参数不能为空");
        sysDictService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody SysDict entity) {
        Assert.notNull(entity,"必填参数不能为空");
        sysDictService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        sysDictService.deleteById(id);
        return ApiResult.ok();
    }
}
