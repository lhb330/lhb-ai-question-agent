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
 * 系统权限实体，对应表 {@code sys_permission}。
 * <p>描述菜单、按钮等权限点，与用户权限关联表配合完成授权。</p>
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 权限编码（如 question:add、ai:chat 等），全局唯一 */
    private String permCode;

    /** 权限名称，用于界面展示 */
    private String permName;

    /** 权限类型：1-页面，2-按钮 */
    private Integer permType;

    /** 父级权限 ID，顶级为 0 */
    private Long parentPermId;

    /** 同级排序值 */
    private Integer sort;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
