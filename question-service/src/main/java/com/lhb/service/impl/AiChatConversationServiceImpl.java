package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.AiChatConversationQueryDTO;
import com.lhb.entity.AiChatConversation;
import com.lhb.mapper.AiChatConversationMapper;
import com.lhb.service.AiChatConversationService;
import com.lhb.vo.AiChatConversationVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class AiChatConversationServiceImpl extends ServiceImpl<AiChatConversationMapper, AiChatConversation> implements AiChatConversationService {

    @Override
    public IPage<AiChatConversationVO> pageList(AiChatConversationQueryDTO dto) {
        log.info("AI对话会话查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<AiChatConversation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getUserId()), AiChatConversation::getUserId, dto.getUserId());
        wrapper.eq(StrUtil.isNotBlank(dto.getConversationId()), AiChatConversation::getConversationId, dto.getConversationId());
        wrapper.like(StrUtil.isNotBlank(dto.getConversationName()), AiChatConversation::getConversationName, dto.getConversationName());
        wrapper.eq(StrUtil.isNotBlank(dto.getChatType()), AiChatConversation::getChatType, dto.getChatType());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getStatus()), AiChatConversation::getStatus, dto.getStatus());
        wrapper.orderByDesc(AiChatConversation::getLastMsgTime);
        Page<AiChatConversation> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            AiChatConversationVO vo = new AiChatConversationVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiChatConversation create(AiChatConversation entity) {
        log.info("AI对话会话新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AiChatConversation update(AiChatConversation entity) {
        log.info("AI对话会话编辑参数：{}", JSONUtil.toJsonStr(entity));
        entity.setUpdateTime(LocalDateTime.now());
        this.updateById(entity);
        return this.getById(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("AI对话会话删除参数：id={}", id);
        return this.removeById(id);
    }
}
