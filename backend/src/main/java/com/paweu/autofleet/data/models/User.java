package com.paweu.autofleet.data.models;

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
    private String userId;
    private String email;
    private String password;
    private String username;
    private String refToken;

    @Override
    public String toString() {
        return "[ user_id=" + userId + ", email=" + email + ", password=" + password + ", username=" + username + ", refToken=" + refToken + " ]";
    }
}
