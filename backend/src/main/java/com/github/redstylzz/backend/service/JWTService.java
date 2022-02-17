package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.model.MongoUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTService {

    @Value("${secret}")
    private String secret;

    public String createToken(MongoUser user) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(12))))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        return (!token.isBlank() && extractUsername(token).equals(username));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
