package com.lhb.questionweb.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lhb.ApiResult;
import com.lhb.PageResult;
import com.lhb.dto.AiChatConversationQueryDTO;
import com.lhb.entity.AiChatConversation;
import com.lhb.service.AiChatConversationService;
import com.lhb.vo.AiChatConversationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai/chatConversation")
@RequiredArgsConstructor
public class AiChatConversationController {

    private final AiChatConversationService aiChatConversationService;

    @GetMapping("/{id}")
    public ApiResult<AiChatConversation> getById(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        return ApiResult.ok(aiChatConversationService.getById(id));
    }

    @GetMapping("/list")
    public ApiResult<List<AiChatConversation>> list() {
        return ApiResult.ok(aiChatConversationService.list());
    }

    @PostMapping("/page")
    public PageResult<AiChatConversationVO> page(@RequestBody AiChatConversationQueryDTO dto) {
        IPage<AiChatConversationVO> page = aiChatConversationService.pageList(dto);
        return PageResult.build(page.getRecords(), page.getTotal(), page.getPages(), page.getCurrent(), page.getSize());
    }

    @PostMapping
    public ApiResult<String> create(@RequestBody AiChatConversation entity) {
        Assert.notNull(entity, "必填参数不能为空");
        aiChatConversationService.create(entity);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult<String> update(@RequestBody AiChatConversation entity) {
        Assert.notNull(entity, "必填参数不能为空");
        aiChatConversationService.update(entity);
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResult<String> delete(@PathVariable Long id) {
        Assert.notNull(id, "id参数为空");
        aiChatConversationService.deleteById(id);
        return ApiResult.ok();
    }
}
