package com.ja.finalproject.domain.user.dto;

import java.util.Date;

import lombok.Data;

@Data
public class HobbyCategoryDto {
    private int id;
    private String hobby_name;
    private Date created_at;
}