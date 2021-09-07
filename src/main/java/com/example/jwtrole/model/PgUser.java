package com.example.jwtrole.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pgusers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PgUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private List<UserRole> roles;
}
