package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.AiQuestionVectorDTO;
import com.lhb.entity.AiQuestionVector;
import com.lhb.service.AiQuestionVectorService;
import com.lhb.vo.AiQuestionVectorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai/question")
@RequiredArgsConstructor
public class AiQuestionVectorController {

    private final AiQuestionVectorService aiQuestionVectorService;

    @GetMapping("/{id}")
    public ApiResult<AiQuestionVector> getById(@PathVariable Long id) {
        return ApiResult.ok(aiQuestionVectorService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<AiQuestionVector>> list() {
        return ApiResult.ok(aiQuestionVectorService.list());
    }

    @PostMapping("/page")
    public PageResult<AiQuestionVectorVO> page(@RequestBody AiQuestionVectorDTO dto) {
        IPage<AiQuestionVectorVO> page = aiQuestionVectorService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody AiQuestionVector entity) {
        aiQuestionVectorService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody AiQuestionVector entity) {
        aiQuestionVectorService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        aiQuestionVectorService.deleteById(id);
        return ApiResult.ok();
    }
}
