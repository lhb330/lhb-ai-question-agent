package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.KnowledgePointDTO;
import com.lhb.entity.KnowledgePoint;
import com.lhb.service.KnowledgePointService;
import com.lhb.vo.KnowledgePointVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kb/point")
@RequiredArgsConstructor
public class KnowledgePointController {

    private final KnowledgePointService knowledgePointService;

    @GetMapping("/{id}")
    public ApiResult<KnowledgePointVO> getById(@PathVariable Long id) {
        return ApiResult.ok(knowledgePointService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<KnowledgePointVO>> list() {
        List<KnowledgePointVO> list = knowledgePointService.getList();
        return ApiResult.ok(list);
    }

    @PostMapping("/page")
    public PageResult<KnowledgePointVO> page(@RequestBody KnowledgePointDTO dto) {
        IPage<KnowledgePointVO> page = knowledgePointService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody KnowledgePoint entity) {
        knowledgePointService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody KnowledgePoint entity) {
        knowledgePointService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        knowledgePointService.deleteById(id);
        return ApiResult.ok();
    }

    @GetMapping("/parent")
    public ApiResult<List<KnowledgePointVO>> getKpParent() {
        List<KnowledgePointVO> parent = knowledgePointService.getKpParent();
        return ApiResult.ok(parent);
    }

    @PostMapping("/child")
    public ApiResult<List<KnowledgePointVO>> getKpChild(@RequestBody List<Long> parentKpIds){
        List<KnowledgePointVO> child = this.knowledgePointService.getKpChild(parentKpIds);
        return ApiResult.ok(child);
    }
}
