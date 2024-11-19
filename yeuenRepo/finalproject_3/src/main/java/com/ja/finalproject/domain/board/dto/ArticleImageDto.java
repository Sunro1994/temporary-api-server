package com.ja.finalproject.domain.board.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleImageDto {
    private int id;
    private int article_id;
    private String location;
    private String original_filename;
    private Date created_at;
}
