package com.sb.solutions.web.user.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String newPassword;
    private String oldPassword;


}
