package com.example.jwtrole.controller;

import com.example.jwtrole.model.PgUser;
import com.example.jwtrole.service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody PgUser user) throws Exception {
        return authService.login(user);
    }

}
