package com.lhb.vo;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.lhb.entity.ClassesInfo;
import com.lhb.entity.GradeInfo;
import com.lhb.entity.SchoolInfo;
import com.lhb.enums.UserTypeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuthUserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 登录账号，唯一 */
    private String userAccount;

    /** 用户姓名 */
    private String userName;

    /** 用户类型： 用户类型（对应字典表中字段dict_type:dict_code）*/
    private String userType;

    private String saToken;

    private Long expire;

    private String userTypeName;

    private String userRoleName;

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
    private Long version;

    public String getUserTypeName() {
        UserTypeEnum anEnum = UserTypeEnum.getEnumByValue(this.userType);
        return ObjUtil.isEmpty(anEnum) ? null : anEnum.getMsg();
    }

    public String getUserRoleName() {
        UserTypeEnum anEnum = UserTypeEnum.getEnumByValue(this.userType);
        return ObjUtil.isEmpty(anEnum) ? null : anEnum.getValue();
    }

}
