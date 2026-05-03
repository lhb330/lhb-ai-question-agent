package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.PaperQuestionRelationQueryDTO;
import com.lhb.entity.PaperQuestionRelation;
import com.lhb.vo.PaperQuestionRelationVO;

public interface PaperQuestionRelationService extends IService<PaperQuestionRelation> {

    IPage<PaperQuestionRelationVO> pageList(PaperQuestionRelationQueryDTO dto);

    PaperQuestionRelation create(PaperQuestionRelation entity);

    PaperQuestionRelation update(PaperQuestionRelation entity);

    boolean deleteById(Long id);
}
