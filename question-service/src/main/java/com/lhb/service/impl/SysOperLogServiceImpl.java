package com.lhb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lhb.dto.SysOperLogQueryDTO;
import com.lhb.entity.SysOperLog;
import com.lhb.mapper.SysOperLogMapper;
import com.lhb.service.SysOperLogService;
import com.lhb.vo.SysOperLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    @Override
    public IPage<SysOperLogVO> pageList(SysOperLogQueryDTO dto) {
        log.info("操作日志查询条件参数：{}", JSONUtil.toJsonStr(dto));
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjUtil.isNotEmpty(dto.getUserId()), SysOperLog::getUserId, dto.getUserId());
        wrapper.eq(StrUtil.isNotBlank(dto.getUserAccount()), SysOperLog::getUserAccount, dto.getUserAccount());
        wrapper.eq(StrUtil.isNotBlank(dto.getOperModule()), SysOperLog::getOperModule, dto.getOperModule());
        wrapper.eq(StrUtil.isNotBlank(dto.getOperType()), SysOperLog::getOperType, dto.getOperType());
        wrapper.eq(ObjUtil.isNotEmpty(dto.getOperStatus()), SysOperLog::getOperStatus, dto.getOperStatus());
        wrapper.orderByDesc(SysOperLog::getOperTime);
        Page<SysOperLog> page = this.page(dto.getPage(), wrapper);
        return page.convert(entity -> {
            SysOperLogVO vo = new SysOperLogVO();
            BeanUtil.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysOperLog create(SysOperLog entity) {
        log.info("操作日志新增参数：{}", JSONUtil.toJsonStr(entity));
        this.save(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        log.info("操作日志删除参数：id={}", id);
        return this.removeById(id);
    }
}
