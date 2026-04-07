package com.lhb.questionweb.controller;

import com.lhb.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ApiResult<String> healthCheck() {
        return ApiResult.ok();
    }
}

