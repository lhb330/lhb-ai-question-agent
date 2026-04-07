package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.LoginDTO;
import com.lhb.dto.UserQueryDTO;
import com.lhb.entity.SysUser;
import com.lhb.vo.AuthUserVO;
import com.lhb.vo.SysUserVO;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    IPage<SysUserVO> pageList(UserQueryDTO dto);

    SysUser create(SysUser entity);

    SysUser update(SysUser entity);

    boolean deleteById(Long id);

    AuthUserVO userLogin(LoginDTO dto);

    void userLogout(LoginDTO dto);

    String getEncryptPassword(String userPassword);

    SysUserVO getUserById(Long id);

    boolean resetPassword(List<Long> userIds);

    Map<Long,SysUser> getUserMap(List<Long> ids);
}
