// TodoDTO.java - This helps abstract the entity from the API
package com.todoapp.dto;
public record TodoDTO(Long id, String task, boolean completed) {}