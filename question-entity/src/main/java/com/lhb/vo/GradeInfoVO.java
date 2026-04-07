package com.lhb.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class GradeInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 年级编码
     */
    private String gradeCode;

    /**
     * 年级名称（一年级、初一、高一等）
     */
    private String gradeName;

    /**
     * 所属学校ID
     */
    private Long schoolId;
    private String schoolName;

    /**
     * 状态（1-启用，0-禁用）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 版本号
     */
    private Long version;

}
