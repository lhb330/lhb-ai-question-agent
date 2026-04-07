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
 * 科目信息实体，对应表 {@code t_subject_info}。
 * <p>同一科目在不同学段可有不同记录，科目编码与学段编码联合唯一。</p>
 */
@Data
@TableName("t_subject_info")
public class SubjectInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 科目编码 */
    private String subjectCode;

    /** 科目名称，如语文、数学、英语 */
    private String subjectName;

    /** 学段编码，可与字典表 stage 类型关联 */
    private String stageCode;

    /** 展示排序值 */
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
