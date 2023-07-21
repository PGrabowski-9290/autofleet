package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;
@Data
@Table(name= "public.user")
public class User {
    @Id @Column("user_id")
    private UUID userId;
    @Column("email")
    private String email;
    @Column("password_hash")
    private String password;
    @Column("username")
    private String username;
    @Column("reftoken")
    private String refToken;


    public User(String email, String password, String username, String refToken) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.refToken = refToken;
    }
}
