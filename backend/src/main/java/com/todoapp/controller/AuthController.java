package com.todoapp.controller;

import com.todoapp.dto.AuthRequest;
import com.todoapp.dto.AuthResponse;
import com.todoapp.dto.RegisterRequest;
import com.todoapp.entity.User;
import com.todoapp.repository.UserRepository;
import com.todoapp.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            log.warn("Registration failed: Username '{}' is already taken.", request.username());
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        String encodedPassword = passwordEncoder.encode(request.password());

        // Log the user being registered and their hashed password
        log.info("Registering user: {}. Hashed Password: {}", request.username(), encodedPassword);

        User newUser = User.builder()
                .username(request.username())
                .password(encodedPassword)
                .build();
        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Log the login attempt
        log.info("Login attempt for user: {}", request.username());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        final String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}