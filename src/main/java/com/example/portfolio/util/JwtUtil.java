package com.example.portfolio.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.portfolio.exception.AuthFailedException;
import com.example.portfolio.response.AuthCheckResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final Algorithm algorithm;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600_000))
                .sign(algorithm);
    }

    public AuthCheckResponse validateToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);
            return new AuthCheckResponse(jwt.getSubject());
        } catch (TokenExpiredException e) {
            throw new AuthFailedException("トークンの有効期限が切れています");
        } catch (JWTVerificationException e) {
            throw new AuthFailedException("不正なトークンです");
        }
    }
}
