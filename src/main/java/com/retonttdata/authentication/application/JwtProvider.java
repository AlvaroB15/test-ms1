package com.retonttdata.authentication.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtProvider {
    private final String secret;
    private final long expiration;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration}") long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String email) {
        long currentTime = System.currentTimeMillis();
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date(currentTime))
                .withExpiresAt(new Date(currentTime + expiration))
                .sign(Algorithm.HMAC256(secret));
    }
}