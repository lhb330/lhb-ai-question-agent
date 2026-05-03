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
 * 系统操作日志实体，对应表 {@code sys_oper_log}。
 * <p>记录用户的后台操作行为，便于审计与追溯。</p>
 */
@Data
@TableName("sys_oper_log")
public class SysOperLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 用户账号 */
    private String userAccount;

    /** 操作模块 */
    private String operModule;

    /** 操作类型 */
    private String operType;

    /** 操作内容描述 */
    private String operContent;

    /** 操作 IP */
    private String operIp;

    /** 操作地点 */
    private String operLocation;

    /** 操作状态：1-成功，0-失败 */
    private Integer operStatus;

    /** 失败时的错误信息 */
    private String errorMsg;

    /** 操作时间 */
    private LocalDateTime operTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
