package com.lhb.vo;


import com.lhb.enums.StageCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SubjectInfoVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 科目编码 */
    private String subjectCode;

    /** 科目名称，如语文、数学、英语 */
    private String subjectName;

    /** 学段编码，可与字典表 stage 类型关联 */
    private String stageCode;
    private String stageName;

    /** 展示排序值 */
    private Integer sort;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    private Long version;

}
