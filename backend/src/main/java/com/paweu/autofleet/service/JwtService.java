package com.paweu.autofleet.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@NoArgsConstructor
public class JwtService {
    @Value("${jwt.secret.accessToken}")
    private String secretAccessToken;

    @Value("${jwt.secret.refreshToken}")
    private String secretRefreshToken;

    @Value("${jwt.expire.accessToken}")
    private long accessTokenExpires;

    @Value("${jwt.expire.refreshToken}")
    private long refreshTokenExpires;

    public long getRefreshTokenExpires() {
        return refreshTokenExpires;
    }

    private String generate(String sub, long expires, Algorithm algorithm){
        return JWT.create()
                .withSubject(sub)
                .withExpiresAt(new Date(new Date().getTime() + expires))
                .sign(algorithm);
    }

    public String generateAccessToken(String email){
        return generate(
                email,
                accessTokenExpires,
                Algorithm.HMAC512(secretAccessToken)
        );
    }

    public String generateRefreshToken(String email){
        return generate(
                email,
                refreshTokenExpires,
                Algorithm.HMAC512(secretRefreshToken)
        );
    }

    public String validateRefresh(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretRefreshToken))
                .withClaimPresence("sub")
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }

    public String validateAccess(String token){
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretAccessToken))
                .withClaimPresence("sub")
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }
}
