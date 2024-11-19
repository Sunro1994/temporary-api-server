package com.ja.finalproject.domain.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MailAuthDto {
    private int id;
    private int user_id;
    private String auth_key;
    private String complete;
    private Date created_at;
}
