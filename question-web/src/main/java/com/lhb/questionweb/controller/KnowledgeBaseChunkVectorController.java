package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.KnowledgeBaseChunkQueryDTO;
import com.lhb.entity.KnowledgeBaseChunkVector;
import com.lhb.service.KnowledgeBaseChunkVectorService;
import com.lhb.vo.KnowledgeBaseChunkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kb/chunk")
@RequiredArgsConstructor
public class KnowledgeBaseChunkVectorController {

    private final KnowledgeBaseChunkVectorService knowledgeBaseChunkVectorService;

    @GetMapping("/{id}")
    public ApiResult<KnowledgeBaseChunkVector> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(knowledgeBaseChunkVectorService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<KnowledgeBaseChunkVector>> list() {
        return ApiResult.ok(knowledgeBaseChunkVectorService.list());
    }

    @PostMapping("/page")
    public PageResult<KnowledgeBaseChunkVO> page(@RequestBody KnowledgeBaseChunkQueryDTO dto) {
        IPage<KnowledgeBaseChunkVO> page = knowledgeBaseChunkVectorService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody KnowledgeBaseChunkVector entity) {
        Assert.notNull(entity, "必填参数不能为空");
        knowledgeBaseChunkVectorService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody KnowledgeBaseChunkVector entity) {
        Assert.notNull(entity, "必填参数不能为空");
        knowledgeBaseChunkVectorService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        knowledgeBaseChunkVectorService.deleteById(id);
        return ApiResult.ok();
    }
}
