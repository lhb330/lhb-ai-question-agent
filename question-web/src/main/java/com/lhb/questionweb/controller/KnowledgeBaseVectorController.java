package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.entity.KnowledgeBase;
import com.lhb.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kb/vector")
@RequiredArgsConstructor
public class KnowledgeBaseVectorController {

    private final KnowledgeBaseService knowledgeBaseVectorService;

    @GetMapping("/{id}")
    public ApiResult<KnowledgeBase> getById(@PathVariable Long id) {
        return ApiResult.ok(knowledgeBaseVectorService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<KnowledgeBase>> list() {
        return ApiResult.ok(knowledgeBaseVectorService.list());
    }

    @GetMapping("/page")
    public PageResult<KnowledgeBase> page(@RequestParam(defaultValue = "1") long pageNum,
                               @RequestParam(defaultValue = "10") long pageSize) {
        IPage<KnowledgeBase> page = knowledgeBaseVectorService.pageList(pageNum, pageSize);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<KnowledgeBase> create(@RequestBody KnowledgeBase entity) {
        return ApiResult.ok(knowledgeBaseVectorService.create(entity));
    }

    @PutMapping
    public ApiResult<KnowledgeBase> update(@RequestBody KnowledgeBase entity) {
        return ApiResult.ok(knowledgeBaseVectorService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(knowledgeBaseVectorService.deleteById(id));
    }
}
