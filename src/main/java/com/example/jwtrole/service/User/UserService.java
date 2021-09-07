package com.example.jwtrole.service.User;

import com.example.jwtrole.model.PgUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<PgUser> getAllUsers();
    PgUser register(PgUser user);
    PgUser findByEmail(String email);
}
