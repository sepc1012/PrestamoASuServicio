package com.prestamosasuservicio.backend.auth.controller;

import com.prestamosasuservicio.backend.auth.dto.AuthResponse;
import com.prestamosasuservicio.backend.auth.dto.LoginRequest;
import com.prestamosasuservicio.backend.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}