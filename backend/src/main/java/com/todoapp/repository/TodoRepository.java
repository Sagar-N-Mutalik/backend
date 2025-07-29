// sagar-n-mutalik/backend/backend-8504ec860ca0048563c538b23a17e80f4ea23756/backend/src/main/java/com/todoapp/repository/TodoRepository.java
package com.todoapp.repository;

import com.todoapp.entity.Todo;
import com.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // This is the correct way to find todos by the User entity
    List<Todo> findByUser(User user);
}