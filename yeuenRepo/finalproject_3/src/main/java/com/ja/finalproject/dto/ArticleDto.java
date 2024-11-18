package com.ja.finalproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleDto {
    private int id;
    private int user_id;
    private String title;
    private String content;
    private int read_count;
    private Date created_at;
}
