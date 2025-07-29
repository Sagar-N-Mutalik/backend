package com.todoapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todoapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}


// import java.util.Optional;
// This imports the Optional class, which is a container object that may or may not contain a non-null value.


// What is Optional and why is it used?

// Optional is a class in Java 8 and later that acts as a container for a value that may or may not be present. It is used to:

// Prevent NullPointerExceptions:
// Instead of returning null when a value is not found, Optional returns an Optional.empty() instance. This forces the developer to explicitly handle the case where the value might be absent, reducing the risk of NullPointerExceptions.

// Improve Code Readability and Expressiveness:
// It makes the intent clear that a method might not return a value. Consumers of the method are encouraged to use methods like isPresent(), orElse(), orElseThrow(), or ifPresent() to handle the presence or absence of the value, leading to more robust and readable code.

// In the findByUsername method, Optional<User> is used because a user with the given username might not exist in the database. If a user is found, the Optional will contain the User object. If no user is found, the Optional will be empty. This design encourages handling the "not found" scenario explicitly.