package com.example.internshipproject.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY = "your_secret_key";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
