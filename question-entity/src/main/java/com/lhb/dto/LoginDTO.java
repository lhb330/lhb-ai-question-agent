package com.lhb.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private Long id;

    private String userAccount;

    private String userPassword;
}
