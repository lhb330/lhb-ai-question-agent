package com.lhb.questionweb.controller;


import com.lhb.ApiResult;
import com.lhb.dto.LoginDTO;
import com.lhb.enums.ErrorCode;
import com.lhb.service.SysUserService;
import com.lhb.vo.AuthUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysUserService sysUserService;


    @PostMapping("/login")
    public ApiResult<AuthUserVO> login(@RequestBody LoginDTO dto) {
        AuthUserVO vo = this.sysUserService.userLogin(dto);
        if (null == vo) {
            return ApiResult.fail(ErrorCode.AUTH_USER_PWD_ERROR);
        }
        return ApiResult.ok(vo);
    }


    @PostMapping("/logout")
    public ApiResult<String> logout(@RequestBody LoginDTO dto) {
        this.sysUserService.userLogout(dto);
        return ApiResult.ok();
    }
}
