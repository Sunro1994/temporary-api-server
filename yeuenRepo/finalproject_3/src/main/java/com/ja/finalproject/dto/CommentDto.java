package com.ja.finalproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {
    private int id;
    private int article_id;
    private int user_id;
    private String content;
    private Date created_at;
}
