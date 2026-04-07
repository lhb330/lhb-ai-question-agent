package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.SchoolInfo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SchoolInfoDTO extends PageRequest<SchoolInfo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
    private Long version;

}
