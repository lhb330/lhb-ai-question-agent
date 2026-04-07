package com.lhb.questionweb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.entity.AiChatMessage;
import com.lhb.service.AiChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai/chat-message")
@RequiredArgsConstructor
public class AiChatMessageController {

    private final AiChatMessageService aiChatMessageService;

    @GetMapping("/{id}")
    public ApiResult<AiChatMessage> getById(@PathVariable Long id) {
        return ApiResult.ok(aiChatMessageService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<AiChatMessage>> list() {
        return ApiResult.ok(aiChatMessageService.list());
    }

    @GetMapping("/page")
    public PageResult<AiChatMessage> page(@RequestParam(defaultValue = "1") long pageNum,
                                          @RequestParam(defaultValue = "10") long pageSize) {
        IPage<AiChatMessage> page = aiChatMessageService.pageList(pageNum, pageSize);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<AiChatMessage> create(@RequestBody AiChatMessage entity) {
        return ApiResult.ok(aiChatMessageService.create(entity));
    }

    @PutMapping
    public ApiResult<AiChatMessage> update(@RequestBody AiChatMessage entity) {
        return ApiResult.ok(aiChatMessageService.update(entity));
    }

    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable Long id) {
        return ApiResult.ok(aiChatMessageService.deleteById(id));
    }
}
