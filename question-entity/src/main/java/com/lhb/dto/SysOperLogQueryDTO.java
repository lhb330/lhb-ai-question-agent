package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.SysOperLog;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志查询 DTO
 */
@Data
public class SysOperLogQueryDTO extends PageRequest<SysOperLog> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String userAccount;
    private String operModule;
    private String operType;
    private String operContent;
    private String operIp;
    private String operLocation;
    private Integer operStatus;
    private String errorMsg;
    private LocalDateTime operTime;
    private Long version;
}
