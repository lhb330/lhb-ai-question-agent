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
 * 知识点实体，对应表 {@code t_knowledge_point}。
 * <p>按科目、学段组织树形或扁平知识点，供出题与检索关联。</p>
 */
@Data
@TableName("t_knowledge_point")
public class KnowledgePoint implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 知识点编码，与科目组合唯一 */
    private String kpCode;

    /** 知识点名称 */
    private String kpName;

    /** 科目 ID */
    private Long subjectId;

    /** 父级知识点 ID，顶级为 0 */
    private Long parentKpId;

    /** 学段编码 */
    private String stageCode;

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
