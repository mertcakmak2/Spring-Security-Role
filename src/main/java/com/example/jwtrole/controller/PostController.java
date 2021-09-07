package com.example.jwtrole.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String getAllPost(){
        return "all posts";
    }

}
