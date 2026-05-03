package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysConfigVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String configKey;
    private String configValue;
    private String configDesc;
    private String configType;
    private Integer sort;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long version;
}
