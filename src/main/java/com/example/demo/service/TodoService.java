package com.example.demo.service;

import com.example.demo.repository.TodoRepository;
import com.example.demo.model.TodoEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository repository;
    public String testService() {
        TodoEntity entity = TodoEntity.builder().title("My first Todo item").build();

        repository.save(entity);

        TodoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);

        repository.save(entity);

        log.info("Entity Id: {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(String userId) {

        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update(TodoEntity entity, String origianlTitle) {
        validate(entity);


        log.info("Entity userId: {}", entity.getUserId());
        log.info("Entity title : {}", entity.getTitle());

        TodoEntity original = repository.findByUserIdAndTitle(entity.getUserId(), origianlTitle).orElseThrow(
                () -> new RuntimeException("존재하지 않는 엔티티입니다.")
        );

        original.setTitle(entity.getTitle());
        repository.save(original);

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(String userId, TodoEntity entity) {
        log.info("entity ={}", entity);

        TodoEntity todo = repository.findByUserIdAndTitle(userId, entity.getTitle())
                .orElseThrow(
                        () -> new RuntimeException("찾을 수 없음")
                );
        log.info("entity ={}", todo);
        log.info(String.valueOf(entity.getUserId().equals(todo.getUserId())));


        try {
            repository.deleteByTitleAndUserId(todo.getTitle(),todo.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    public void validate(TodoEntity entity) {
        if( entity == null ) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if( entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public TodoEntity findTodo(String userId, String title) {

        TodoEntity findEntity = repository.findByUserIdAndTitle(userId, title).orElseThrow(
                () -> new RuntimeException("찾을 수 없음")
        );
        return findEntity;
    }
}











