// sagar-n-mutalik/backend/backend-8504ec860ca0048563c538b23a17e80f4ea23756/backend/src/main/java/com/todoapp/service/TodoService.java
package com.todoapp.service;

import com.todoapp.dto.TodoDTO;
import com.todoapp.entity.Todo;
import com.todoapp.entity.User;
import com.todoapp.repository.TodoRepository;
import com.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoDTO> getTodosForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        // Corrected repository method name
        return todoRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TodoDTO addTodo(String username, TodoDTO todoDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        // Create entity from DTO
        Todo todo = new Todo(todoDTO.task(), user);
        Todo saved = todoRepository.save(todo);
        return toDTO(saved);
    }

    public TodoDTO updateTodo(String username, Long todoId, TodoDTO todoDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Todo todo = todoRepository.findById(todoId)
                // Make sure the todo belongs to the user
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        todo.setTask(todoDTO.task()); // Use 'task' field
        todo.setCompleted(todoDTO.completed());
        Todo updated = todoRepository.save(todo);
        return toDTO(updated);
    }

    public void deleteTodo(String username, Long todoId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Todo todo = todoRepository.findById(todoId)
                .filter(t -> t.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        todoRepository.delete(todo);
    }

    // Helper method to convert Entity to DTO
    private TodoDTO toDTO(Todo todo) {
        return new TodoDTO(todo.getId(), todo.getTask(), todo.isCompleted());
    }
}