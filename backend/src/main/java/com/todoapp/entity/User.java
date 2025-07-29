// sagar-n-mutalik/backend/backend-8504ec860ca0048563c538b23a17e80f4ea23756/backend/src/main/java/com/todoapp/entity/User.java
package com.todoapp.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@Builder // Add this annotation
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    public User(Long id, String username, String password, List<Todo> todos) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.todos = todos;
    }
}