package com.lhb.questionweb.controller;


import com.lhb.ApiResult;
import com.lhb.dto.AiQuestionVectorDTO;

import com.lhb.service.AiQuestionVectorService;
import com.lhb.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * AI question generation endpoint.
 */
@RestController
@RequestMapping("/ai/question")
@RequiredArgsConstructor
public class QuestionGenerateController {

    private final AiQuestionVectorService aiQuestionVectorService;


    @PostMapping("/generate")
    public ApiResult<String> generate(@RequestBody AiQuestionVectorDTO request) {
        /*if (!valid(request)) {
            return ApiResult.fail(ErrorCode.PARAMS_NULL_ERROR,"必填参数不能为空");
        }*/
        Long userId = UserUtil.getLoginUser().getId();
        aiQuestionVectorService.saveAiQuestionVector(request,userId);
        return ApiResult.ok("任务已提交，后台生成中");
    }

}
