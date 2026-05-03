package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.QuestionErrorQueryDTO;
import com.lhb.entity.QuestionError;
import com.lhb.service.QuestionErrorService;
import com.lhb.vo.QuestionErrorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/error")
@RequiredArgsConstructor
public class QuestionErrorController {

    private final QuestionErrorService questionErrorService;

    @GetMapping("/{id}")
    public ApiResult<QuestionError> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(questionErrorService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<QuestionError>> list() {
        return ApiResult.ok(questionErrorService.list());
    }

    @PostMapping("/page")
    public PageResult<QuestionErrorVO> page(@RequestBody QuestionErrorQueryDTO dto) {
        IPage<QuestionErrorVO> page = questionErrorService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody QuestionError entity) {
        Assert.notNull(entity, "必填参数不能为空");
        questionErrorService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody QuestionError entity) {
        Assert.notNull(entity, "必填参数不能为空");
        questionErrorService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        questionErrorService.deleteById(id);
        return ApiResult.ok();
    }
}
