package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.QuestionCollectQueryDTO;
import com.lhb.entity.QuestionCollect;
import com.lhb.vo.QuestionCollectVO;

public interface QuestionCollectService extends IService<QuestionCollect> {

    IPage<QuestionCollectVO> pageList(QuestionCollectQueryDTO dto);

    QuestionCollect create(QuestionCollect entity);

    QuestionCollect update(QuestionCollect entity);

    boolean deleteById(Long id);
}
