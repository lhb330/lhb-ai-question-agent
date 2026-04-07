package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.SysLoginLog;
import com.lhb.entity.SysUser;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysLoginLogDTO extends PageRequest<SysLoginLog> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户 ID，关联 {@link SysUser} */
    private Long userId;

    /** 本次登录使用的账号 */
    private String loginAccount;

    /** 登录 IP */
    private String loginIp;

    /** 登录地点（可由 IP 解析得到） */
    private String loginLocation;

    /** 登录设备或客户端标识 */
    private String loginDevice;

    /** 登录时间 */
    private LocalDateTime loginTime;

    /** 登出时间，未登出可为空 */
    private LocalDateTime logoutTime;

    /** 乐观锁版本号 */
    private Long version;

}
