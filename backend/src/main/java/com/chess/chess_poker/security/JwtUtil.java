package com.chess.chess_poker.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final Key key = Jwts.SIG.HS256.key().build();
    private final long expiration = 1000 * 60 * 60 * 24;

    public String generateToken(String username){
        return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(key)
        .compact();
    }
    public String extractUsername(String token){
        return Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }

    }
}
