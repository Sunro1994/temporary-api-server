package com.example.demo.service;

import com.example.demo.repository.TodoRepository;
import com.example.demo.model.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
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

    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent( todo -> {
            System.out.println("todo.getId() = " + todo.getId());
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todo.setUserId(entity.getUserId());


            repository.saveAndFlush(todo);
        }  );

        return retrieve(entity.getUserId());

    }

    public List<TodoEntity> delete(TodoEntity entity) {
        log.info("entity ={}", entity);

        TodoEntity todo = repository.findByTitle(entity.getTitle());


        try {
            repository.delete(todo);
        } catch (Exception e) {
            log.error("error deleting entity  ", entity.getId(), e);
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

    public TodoEntity findTodo(String title) {

        TodoEntity findEntity = repository.findByTitle(title);
        return findEntity;
    }
}











