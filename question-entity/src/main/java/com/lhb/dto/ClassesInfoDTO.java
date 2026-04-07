package com.lhb.dto;

import com.lhb.PageRequest;
import com.lhb.entity.ClassesInfo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class ClassesInfoDTO extends PageRequest<ClassesInfo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;



}
