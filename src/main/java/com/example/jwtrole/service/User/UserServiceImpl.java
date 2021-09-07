package com.example.jwtrole.service.User;

import com.example.jwtrole.model.PgUser;
import com.example.jwtrole.model.Role;
import com.example.jwtrole.model.UserRole;
import com.example.jwtrole.repository.PgUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final PgUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // only with authorities

      /*  PgUser pgUser = findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserRole role: pgUser.getRoles() ) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }

        User user = new User(pgUser.getEmail(), pgUser.getPassword(), authorities);
        return user;*/

        PgUser pgUser = findByEmail(email);
        List<String> authorities = new ArrayList<>();
        String[] roles_array= new String[pgUser.getRoles().size()];

        for (UserRole role: pgUser.getRoles() ) {
            authorities.add(role.getRole().name());
        }
        roles_array = authorities.toArray(roles_array);

        UserDetails userDetails = User
                .withUserDetails(new User(pgUser.getEmail(), pgUser.getPassword(), new ArrayList<>()))
                .roles(roles_array)
                .build();
        return userDetails;
    }

    @Override
    public List<PgUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public PgUser register(PgUser user) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public PgUser findByEmail(String email){
        PgUser user = userRepository.findByEmail(email);
        return user;
    }
}
