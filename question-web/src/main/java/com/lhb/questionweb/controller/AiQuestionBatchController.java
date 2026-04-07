package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.entity.AiQuestionBatch;
import com.lhb.service.AiQuestionBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai/question-batch")
@RequiredArgsConstructor
public class AiQuestionBatchController {

    private final AiQuestionBatchService aiQuestionBatchService;

    @GetMapping("/{id}")
    public ApiResult<AiQuestionBatch> getById(@PathVariable Long id) {
        return ApiResult.ok(aiQuestionBatchService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<AiQuestionBatch>> list() {
        return ApiResult.ok(aiQuestionBatchService.list());
    }

    @GetMapping("/page")
    public PageResult<AiQuestionBatch> page(@RequestParam(defaultValue = "1") long pageNum,
                               @RequestParam(defaultValue = "10") long pageSize) {
        IPage<AiQuestionBatch> page = aiQuestionBatchService.pageList(pageNum, pageSize);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<AiQuestionBatch> create(@RequestBody AiQuestionBatch entity) {
        return ApiResult.ok(aiQuestionBatchService.create(entity));
    }

    @PutMapping
    public ApiResult<AiQuestionBatch> update(@RequestBody AiQuestionBatch entity) {
        return ApiResult.ok(aiQuestionBatchService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(aiQuestionBatchService.deleteById(id));
    }
}
