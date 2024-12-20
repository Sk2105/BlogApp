package com.sk.blogapp.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtils {

    private final String secret = "secret";

    public String generateToken(String email) {
        return JWT.create().withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("BlogApp")
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).withIssuer("BlogApp").build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secret)).withIssuer("BlogApp").build().verify(token).getClaim("email")
                    .asString();
        } catch (Exception e) {
            return null;
        }
    }

}
