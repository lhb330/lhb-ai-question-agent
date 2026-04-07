package com.lhb.vo;

import com.lhb.enums.StageCodeEnum;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class KnowledgePointVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /** 知识点编码，与科目组合唯一 */
    private String kpCode;

    /** 知识点名称 */
    private String kpName;

    /** 科目 ID */
    private Long subjectId;
    private String subjectName;

    /** 父级知识点 ID，顶级为 0 */
    private Long parentKpId;
    private String parentKpName;

    /** 学段编码 */
    private String stageCode;
    private String stageName;

    /** 同级排序值 */
    private Integer sort;

    /** 状态：1-启用，0-禁用 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 乐观锁版本号 */
    private Long version;

    public String getStageName() {
        StageCodeEnum value = StageCodeEnum.getEnumByValue(this.stageCode);
        return null != value ? value.getMsg() : null;
    }

}
