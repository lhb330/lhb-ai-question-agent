package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.QuestionErrorQueryDTO;
import com.lhb.entity.QuestionError;
import com.lhb.vo.QuestionErrorVO;

public interface QuestionErrorService extends IService<QuestionError> {

    IPage<QuestionErrorVO> pageList(QuestionErrorQueryDTO dto);

    QuestionError create(QuestionError entity);

    QuestionError update(QuestionError entity);

    boolean deleteById(Long id);
}
