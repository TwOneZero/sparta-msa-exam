package com.sparta.msa_exam.auth;


import com.sparta.msa_exam.auth.dto.AuthResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {
    private final SecretKey secretKey;
    @Value("${spring.application.name}")
    private String issuer;
    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    public AuthService(@Value("${service.jwt.secret-key}") String secretKey
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    public AuthResponse signIn(String userId) {
        return new AuthResponse(createAccessToken(userId));
    }


    public String createAccessToken(String userId) {
        return Jwts.builder()
                .claim("user_id", userId)
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }
}
