package com.example.jwtrole.repository;

import com.example.jwtrole.model.PgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PgUserRepository extends JpaRepository<PgUser, Integer> {

    PgUser findByEmail(String email);
}
