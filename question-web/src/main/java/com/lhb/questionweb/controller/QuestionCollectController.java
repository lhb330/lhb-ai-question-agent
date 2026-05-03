package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.QuestionCollectQueryDTO;
import com.lhb.entity.QuestionCollect;
import com.lhb.service.QuestionCollectService;
import com.lhb.vo.QuestionCollectVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/collect")
@RequiredArgsConstructor
public class QuestionCollectController {

    private final QuestionCollectService questionCollectService;

    @GetMapping("/{id}")
    public ApiResult<QuestionCollect> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(questionCollectService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<QuestionCollect>> list() {
        return ApiResult.ok(questionCollectService.list());
    }

    @PostMapping("/page")
    public PageResult<QuestionCollectVO> page(@RequestBody QuestionCollectQueryDTO dto) {
        IPage<QuestionCollectVO> page = questionCollectService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody QuestionCollect entity) {
        Assert.notNull(entity, "必填参数不能为空");
        questionCollectService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody QuestionCollect entity) {
        Assert.notNull(entity, "必填参数不能为空");
        questionCollectService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        questionCollectService.deleteById(id);
        return ApiResult.ok();
    }
}
