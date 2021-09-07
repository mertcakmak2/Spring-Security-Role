package com.example.jwtrole.service.Auth;

import com.example.jwtrole.jwt.JwtUtil;
import com.example.jwtrole.model.PgUser;
import com.example.jwtrole.model.UserRole;
import com.example.jwtrole.service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public String login(PgUser user) throws Exception {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        PgUser pgUser = userService.findByEmail(user.getEmail());

        boolean isPasswordMatch = bCryptPasswordEncoder.matches(user.getPassword(), pgUser.getPassword());
        if(!isPasswordMatch) throw new IllegalStateException("Geçersiz kullanıcı adı yada şifre");

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role: pgUser.getRoles() ) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(pgUser.getEmail(), pgUser.getPassword(), authorities));
        } catch(Exception e) {
            throw new Exception("authentication failed "+e);
        }

        return jwtUtil.generateToken(pgUser.getEmail());
    }
}
