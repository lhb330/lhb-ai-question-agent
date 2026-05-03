package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.PaperInfoQueryDTO;
import com.lhb.entity.PaperInfo;
import com.lhb.service.PaperInfoService;
import com.lhb.vo.PaperInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paper/info")
@RequiredArgsConstructor
public class PaperInfoController {

    private final PaperInfoService paperInfoService;

    @GetMapping("/{id}")
    public ApiResult<PaperInfo> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(paperInfoService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<PaperInfo>> list() {
        return ApiResult.ok(paperInfoService.list());
    }

    @PostMapping("/page")
    public PageResult<PaperInfoVO> page(@RequestBody PaperInfoQueryDTO dto) {
        IPage<PaperInfoVO> page = paperInfoService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody PaperInfo entity) {
        Assert.notNull(entity, "必填参数不能为空");
        paperInfoService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody PaperInfo entity) {
        Assert.notNull(entity, "必填参数不能为空");
        paperInfoService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        paperInfoService.deleteById(id);
        return ApiResult.ok();
    }
}
