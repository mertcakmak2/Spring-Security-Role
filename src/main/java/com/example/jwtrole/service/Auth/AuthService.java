package com.example.jwtrole.service.Auth;

import com.example.jwtrole.model.PgUser;

public interface AuthService {

    String login(PgUser user) throws Exception;
}
