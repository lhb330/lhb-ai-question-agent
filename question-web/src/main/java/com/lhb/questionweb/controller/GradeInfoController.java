package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.GradeInfoDTO;
import com.lhb.entity.GradeInfo;
import com.lhb.service.GradeInfoService;
import com.lhb.vo.GradeInfoGroupVO;
import com.lhb.vo.GradeInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grade")
@RequiredArgsConstructor
public class GradeInfoController {

    private final GradeInfoService gradeInfoService;

    @GetMapping("/{id}")
    public ApiResult<GradeInfo> getById(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        gradeInfoService.getById(id);
        return ApiResult.ok();
    }

    @GetMapping("/list")
    public ApiResult<List<GradeInfoVO>> list() {
        List<GradeInfoVO> infoAll = gradeInfoService.getGradeInfoAll();
        return ApiResult.ok(infoAll);
    }

    @PostMapping("/page")
    public PageResult<GradeInfoVO> page(@RequestBody GradeInfoDTO dto) {
        IPage<GradeInfoVO> page = gradeInfoService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<GradeInfo> create(@RequestBody GradeInfo entity) {
        return ApiResult.ok(gradeInfoService.create(entity));
    }

    @PutMapping
    public ApiResult<GradeInfo> update(@RequestBody GradeInfo entity) {
        return ApiResult.ok(gradeInfoService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        return ApiResult.ok(gradeInfoService.deleteById(id));
    }


    @GetMapping("/school/{schoolId}")
    public ApiResult<List<GradeInfo>> listGradeInfoBySchoolId(@PathVariable Long schoolId) {
        Assert.notNull(schoolId,"schoolId参数为空");
        return this.listGradeInfoBySchoolIds(List.of(schoolId));
    }


    @PostMapping("/school/schoolIds")
    public ApiResult<List<GradeInfo>> listGradeInfoBySchoolIds(@RequestBody List<Long> schoolIds) {
        Assert.notEmpty(schoolIds,"schoolIds参数为空");
        List<GradeInfo> list = gradeInfoService.getBySchoolIds(schoolIds);
        return ApiResult.ok(list);
    }

    @GetMapping("/group")
    public ApiResult<List<GradeInfoGroupVO>> getGradeInfoGroup() {
        List<GradeInfoGroupVO> list = gradeInfoService.getGradeInfoGroup();
        return ApiResult.ok(list);
    }


}
