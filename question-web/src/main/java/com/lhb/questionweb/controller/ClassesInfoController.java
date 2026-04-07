package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.ClassesInfoDTO;
import com.lhb.entity.ClassesInfo;
import com.lhb.service.ClassesInfoService;
import com.lhb.vo.ClassesInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassesInfoController {


    private final ClassesInfoService classesInfoService;

    @GetMapping("/{id}")
    public ApiResult<ClassesInfo> getById(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        classesInfoService.getById(id);
        return ApiResult.ok();
    }

    @GetMapping("/list")
    public ApiResult<List<ClassesInfo>> list() {
        return ApiResult.ok(classesInfoService.list());
    }

    @PostMapping("/page")
    public PageResult<ClassesInfoVO> page(@RequestBody ClassesInfoDTO dto) {
        IPage<ClassesInfoVO> page = classesInfoService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<ClassesInfo> create(@RequestBody ClassesInfo entity) {
        return ApiResult.ok(classesInfoService.create(entity));
    }

    @PutMapping
    public ApiResult<ClassesInfo> update(@RequestBody ClassesInfo entity) {
        return ApiResult.ok(classesInfoService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        Assert.notNull(id,"id参数为空");
        return ApiResult.ok(classesInfoService.deleteById(id));
    }


    @PostMapping("/school")
    public ApiResult<List<ClassesInfo>> listClassesInfoBySchoolIds(@RequestBody List<Long> schoolIds) {
        Assert.notEmpty(schoolIds,"schoolIds参数为空");
        List<ClassesInfo> list = this.classesInfoService.getBySchoolIds(schoolIds);
        return ApiResult.ok(list);
    }


    @PostMapping("/grade")
    public ApiResult<List<ClassesInfo>> listClassesInfoByGradeIds(@RequestBody List<Long> gradeIds) {
        Assert.notEmpty(gradeIds,"gradeIds参数为空");
        List<ClassesInfo> list = classesInfoService.getByGradeIds(gradeIds);
        return ApiResult.ok(list);
    }

}
