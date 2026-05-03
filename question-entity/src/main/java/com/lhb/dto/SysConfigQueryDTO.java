package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.SysConfig;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统参数配置查询 DTO
 */
@Data
public class SysConfigQueryDTO extends PageRequest<SysConfig> implements Serializable {

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
