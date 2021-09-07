package com.example.jwtrole.controller;

import com.example.jwtrole.model.PgUser;
import com.example.jwtrole.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PgUser findUserByEmail(@PathVariable String email){
        PgUser pgUser = userService.findByEmail(email);
        return pgUser;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<PgUser> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public PgUser register(@RequestBody PgUser user){
        return userService.register(user);
    }

}
