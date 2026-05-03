package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.PaperQuestionRelationQueryDTO;
import com.lhb.entity.PaperQuestionRelation;
import com.lhb.service.PaperQuestionRelationService;
import com.lhb.vo.PaperQuestionRelationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paper/questionRelation")
@RequiredArgsConstructor
public class PaperQuestionRelationController {

    private final PaperQuestionRelationService paperQuestionRelationService;

    @GetMapping("/{id}")
    public ApiResult<PaperQuestionRelation> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(paperQuestionRelationService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<PaperQuestionRelation>> list() {
        return ApiResult.ok(paperQuestionRelationService.list());
    }

    @PostMapping("/page")
    public PageResult<PaperQuestionRelationVO> page(@RequestBody PaperQuestionRelationQueryDTO dto) {
        IPage<PaperQuestionRelationVO> page = paperQuestionRelationService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody PaperQuestionRelation entity) {
        Assert.notNull(entity, "必填参数不能为空");
        paperQuestionRelationService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody PaperQuestionRelation entity) {
        Assert.notNull(entity, "必填参数不能为空");
        paperQuestionRelationService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        paperQuestionRelationService.deleteById(id);
        return ApiResult.ok();
    }
}
