package com.example.springsecurityjwt.payload.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
@Data
@Getter
@Setter
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date created = new Date();
    private boolean userStatus = true;
    private Set<String> listRoles;
}
