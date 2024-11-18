package com.example.demo.repository;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
   Optional<TodoEntity> findByUserIdAndTitle(String userId, String title);

    TodoEntity findByTitle(String title);

    void deleteByTitleAndUserId(String title, String userId);

    List<TodoEntity> findByUserId(String userId);
}
