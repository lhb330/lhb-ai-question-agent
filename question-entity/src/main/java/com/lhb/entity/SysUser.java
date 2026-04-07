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
 * 系统用户实体，对应表 {@code sys_user}。
 * <p>校长、老师、学生等登录账号与基础档案信息。</p>
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 登录账号，唯一 */
    private String userAccount;

    /** 登录密码（应存储加密后的密文） */
    private String userPassword;

    /** 用户姓名 */
    private String userName;

    /** 用户类型：用户类型（对应字典表中字段dict_type:dict_code） */
    private String userType;

    /** 所属学校 ID，关联 {@link SchoolInfo} */
    private Long schoolId;

    /** 关联年级id，关联 {@link GradeInfo} */
    private Long gradeId;

    /** 关联班级id，关联 {@link ClassesInfo} */
    private Long classesId;

    /** 学段编码 */
    private String stageCode;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像地址 URL */
    private String avatar;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 最近一次登录时间 */
    private LocalDateTime lastLoginTime;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    @TableField("\"version\"")
    @Version
    private Long version;
}
