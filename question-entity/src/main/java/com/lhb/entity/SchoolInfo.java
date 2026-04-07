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
 * 学校信息实体，对应表 {@code t_school_info}。
 * <p>支持总校与分校层级，用户表通过 schoolId 关联。</p>
 */
@Data
@TableName("t_school_info")
public class SchoolInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 学校编码，唯一 */
    private String schoolCode;

    /** 学校名称 */
    private String schoolName;

    /** 学校类型：1-总校，2-分校 */
    private Integer schoolType;

    /** 父级学校 ID，分校关联总校；总校为 0 */
    private Long parentSchoolId;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 详细地址 */
    private String address;

    /** 联系电话 */
    private String contactPhone;

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
