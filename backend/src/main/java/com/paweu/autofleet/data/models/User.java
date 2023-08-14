package com.paweu.autofleet.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "public.user")
public class User {
    @Id
    @Column("user_id")
    private UUID userId;
    @Column("email")
    private String email;
    @Column("password_hash")
    private String password;
    @Column("username")
    private String username;
    @Column("reftoken")
    private String refToken;
}
