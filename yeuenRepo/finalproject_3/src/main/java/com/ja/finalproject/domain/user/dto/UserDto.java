package com.ja.finalproject.domain.user.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String user_id;
    private String password;
    private String nickname;
    private String email;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String phone;
    private Date created_at;
}
