package com.todoapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoapp.dto.TodoDTO;
import com.todoapp.service.TodoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000") // Change to your frontend URL
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getTodos(@AuthenticationPrincipal UserDetails userDetails) {
        List<TodoDTO> todos = todoService.getTodosForUser(userDetails.getUsername());
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<TodoDTO> addTodo(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody TodoDTO todoDTO) {
        TodoDTO created = todoService.addTodo(userDetails.getUsername(), todoDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable Long id,
                                              @RequestBody TodoDTO todoDTO) {
        TodoDTO updated = todoService.updateTodo(userDetails.getUsername(), id, todoDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long id) {
        todoService.deleteTodo(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }
}
