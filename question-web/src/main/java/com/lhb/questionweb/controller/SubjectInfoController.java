package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.SubjectInfoDTO;
import com.lhb.entity.SubjectInfo;
import com.lhb.service.SubjectInfoService;
import com.lhb.vo.SubjectGroupVO;
import com.lhb.vo.SubjectInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectInfoController {

    private final SubjectInfoService subjectInfoService;

    @GetMapping("/{id}")
    public ApiResult<SubjectInfoVO> getById(@PathVariable Long id) {
        return ApiResult.ok(subjectInfoService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SubjectInfo>> list() {
        return ApiResult.ok(subjectInfoService.list());
    }

    @PostMapping("/page")
    public PageResult<SubjectInfoVO> page(@RequestBody SubjectInfoDTO dto) {
        IPage<SubjectInfoVO> page = subjectInfoService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<SubjectInfo> create(@RequestBody SubjectInfo entity) {
        return ApiResult.ok(subjectInfoService.create(entity));
    }

    @PutMapping
    public ApiResult<SubjectInfo> update(@RequestBody SubjectInfo entity) {
        return ApiResult.ok(subjectInfoService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(subjectInfoService.deleteById(id));
    }

    @GetMapping("/group")
    public ApiResult<List<SubjectGroupVO>> getSubjectGroupList() {
        List<SubjectGroupVO> list = this.subjectInfoService.getSubjectGroupList();
        return ApiResult.ok(list);
    }
}
