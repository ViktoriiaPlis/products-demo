package com.example.productdemo.service;

import com.example.productdemo.dao.UserDao;
import com.example.productdemo.entity.UserEntity;
import com.example.productdemo.model.UserRole;
import com.example.productdemo.request.AuthRequest;
import com.example.productdemo.response.AuthResponse;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthenticationService {
    private final UserDao userDao;
    private final SecurityService securityService;
    private SecretKey key = Jwts.SIG.HS256.key().build();
    private Duration accessTokenTtl;
    private Duration refreshTokenTtl;

    public AuthenticationService(UserDao userDao, SecurityService securityService,
                                 @Value("${app.accessToken.ttl:15m}") Duration accessTokenTtl,
                                 @Value("${app.refreshToken.ttl:24h}") Duration refreshTokenTtl) {
        this.userDao = userDao;
        this.securityService = securityService;
        this.accessTokenTtl = accessTokenTtl;
        this.refreshTokenTtl = refreshTokenTtl;
    }

    public AuthResponse login(AuthRequest authRequest) {
        Optional<UserEntity> userEntity = userDao.findByLoginAndDeletedAtIsNull(authRequest.getLogin());
        if (userEntity.isPresent()) {
            String salt = userEntity.get().getSalt();
            String expectedHash = userEntity.get().getHash();
            String actualHash = securityService.generatePasswordHash(authRequest.getPassword(), salt);
            if (!expectedHash.equals(actualHash)) {
                throw new IllegalStateException("User not found or password incorrect");
            }
            return new AuthResponse(generateAccessToken(authRequest.getLogin(), UserRole.valueOf(userEntity.get().getRole().toString())),
                    generateRefreshToken(authRequest.getLogin(), UserRole.valueOf(userEntity.get().getRole().toString())));
        }
        throw new IllegalStateException("User not found or password incorrect");
    }
    
    public String generateAccessToken(String login, UserRole role) {
        Instant now = Instant.now();
        Instant accessExpirationInstant = now.plus(accessTokenTtl);
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(login)
                .expiration(accessExpiration)
                .claim("roles", role)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String login, UserRole role) {
        Instant now = Instant.now();
        Instant accessExpirationInstant = now.plus(refreshTokenTtl);
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(login)
                .expiration(accessExpiration)
                .claim("roles", role)
                .signWith(key)
                .compact();
    }
}
