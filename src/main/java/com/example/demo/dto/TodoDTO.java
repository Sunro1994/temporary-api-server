package com.example.demo.dto;

import com.example.demo.model.TodoEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String userid;
    private String originalTitle;
    private String title;
    private boolean done;

    public TodoDTO(TodoEntity entity) {
        this.id = entity.getId();
        this.userid = entity.getUserId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .userId(dto.getUserid())
                .done(dto.isDone())
                .build();
    }
    
}
