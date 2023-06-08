package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String userId;
    private String email;
    private String password;
    private String username;
    private String refToken;

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @Override
    public String toString() {
        return "[ user_id=" + userId + ", email=" + email + ", password=" + password + ", username=" + username + ", refToken=" + refToken + " ]";
    }
}
