package com.example.springsecurityjwt.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddUserDto {
    private String email;
    private String fullName;
    private String phone;
    private String password;
    private Integer roleId;
}
