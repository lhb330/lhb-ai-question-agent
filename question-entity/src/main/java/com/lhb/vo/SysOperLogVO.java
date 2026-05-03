package com.lhb.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysOperLogVO implements Serializable {

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
