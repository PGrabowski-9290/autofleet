package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name= "public.user")
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


    public User(String email, String password, String username, String refToken) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.refToken = refToken;
    }

//    @Override
//    public String toString() {
//        return "[ user_id=" + userId + ", email=" + email + ", password=" + password + ", username=" + username + ", refToken=" + refToken + " ]";
//    }
}
