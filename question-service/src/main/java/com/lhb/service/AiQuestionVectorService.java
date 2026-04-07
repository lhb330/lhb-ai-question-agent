package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.AiQuestionVectorDTO;
import com.lhb.entity.AiQuestionVector;
import com.lhb.vo.AiQuestionVectorVO;
import org.springframework.web.context.request.RequestAttributes;

public interface AiQuestionVectorService extends IService<AiQuestionVector> {

    IPage<AiQuestionVectorVO> pageList(AiQuestionVectorDTO dto);

    AiQuestionVector create(AiQuestionVector entity);

    AiQuestionVector update(AiQuestionVector entity);

    boolean deleteById(Long id);

    void saveAiQuestionVector(AiQuestionVectorDTO dto, Long userId);

    long maxSeq();
}
