package com.example.productdemo.service;

import com.example.productdemo.model.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenUtil {
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String login, Date accessExpiration, UserRole role) {
        return Jwts.builder()
                .subject(login)
                .expiration(accessExpiration)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public Pair<String, UserRole> getAllClaimsFromToken(String token) {
        Jws<Claims> jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        String username = jwt.getPayload().getSubject();
        String userRole = jwt.getPayload().get("role", String.class);
        UserRole role = UserRole.valueOf(userRole);
        return Pair.of(username, role);
    }

    public boolean validateToken(String token) {
        Jws<Claims> jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return jwt.getPayload().getExpiration().after(new Date());
    }
}
