package com.ja.finalproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserHobbyDto {
    private int id;
    private int user_id;
    private int hobby_id;
    private Date created_at;
}
