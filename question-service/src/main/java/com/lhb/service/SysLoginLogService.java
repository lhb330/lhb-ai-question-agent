package com.lhb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lhb.dto.SysLoginLogDTO;
import com.lhb.entity.SysLoginLog;

public interface SysLoginLogService extends IService<SysLoginLog> {

    IPage<SysLoginLog> pageList(SysLoginLogDTO dto);

    SysLoginLog create(SysLoginLog entity);

    SysLoginLog update(SysLoginLog entity);

    boolean deleteById(Long id);

    void saveLoginLog(String loginAccount, Long userId,
                      String loginDevice,String ip,
                      Integer loginStatus, String errorMsg);

    /**
     * 修改登录账号登出时间
     * @param loginAccount
     */
    void updateLogoutTimeByLoginAccount(String loginAccount);
}
