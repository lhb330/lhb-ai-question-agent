package com.lhb.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户登录日志实体，对应表 {@code sys_login_log}。
 * <p>记录每次登录的账号、终端、结果等信息，便于审计与风控。</p>
 */
@Data
@TableName("sys_login_log")
public class SysLoginLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
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

    /** 登录状态：1-成功，0-失败 */
    private Integer loginStatus;

    /** 失败时的错误说明 */
    private String errorMsg;

    /** 登录时间 */
    private LocalDateTime loginTime;

    /** 登出时间，未登出可为空 */
    private LocalDateTime logoutTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
