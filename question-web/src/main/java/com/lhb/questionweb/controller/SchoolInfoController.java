package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.SchoolInfoDTO;
import com.lhb.entity.SchoolInfo;
import com.lhb.service.SchoolInfoService;
import com.lhb.vo.SchoolInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolInfoController {

    private final SchoolInfoService schoolInfoService;

    @GetMapping("/{id}")
    public ApiResult<SchoolInfo> getById(@PathVariable Long id) {
        return ApiResult.ok(schoolInfoService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<SchoolInfo>> list() {
        return ApiResult.ok(schoolInfoService.list());
    }

    @PostMapping("/page")
    public PageResult<SchoolInfoVO> page(@RequestBody SchoolInfoDTO dto) {
        IPage<SchoolInfoVO> page = schoolInfoService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<SchoolInfo> create(@RequestBody SchoolInfo entity) {
        return ApiResult.ok(schoolInfoService.create(entity));
    }

    @PutMapping
    public ApiResult<SchoolInfo> update(@RequestBody SchoolInfo entity) {
        return ApiResult.ok(schoolInfoService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(schoolInfoService.deleteById(id));
    }

    @GetMapping("/parent")
    public ApiResult<List<SchoolInfoVO>> parentSchool() {
        List<SchoolInfoVO> list = schoolInfoService.getByParentSchool();
        return ApiResult.ok(list);
    }

    @PostMapping("/child")
    public ApiResult<List<SchoolInfoVO>> childSchool(@RequestBody List<Long> parentSchoolIds) {
        List<SchoolInfoVO> list = schoolInfoService.getChildByParentSchoolId(parentSchoolIds);
        return ApiResult.ok(list);
    }
}
