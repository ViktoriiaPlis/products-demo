package com.example.productdemo.service;

import com.example.productdemo.dao.UserDao;
import com.example.productdemo.entity.UserEntity;
import com.example.productdemo.model.UserRole;
import com.example.productdemo.request.AuthRequest;
import com.example.productdemo.response.AuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthenticationService {
    private final UserDao userDao;
    private final SecurityService securityService;

    private final JwtTokenUtil jwtTokenUtil;
    private Duration accessTokenTtl;
    private Duration refreshTokenTtl;

    public AuthenticationService(UserDao userDao, SecurityService securityService,
                                 JwtTokenUtil jwtTokenUtil, @Value("${app.accessToken.ttl:15m}") Duration accessTokenTtl,
                                 @Value("${app.refreshToken.ttl:24h}") Duration refreshTokenTtl) {
        this.userDao = userDao;
        this.securityService = securityService;
        this.jwtTokenUtil = jwtTokenUtil;
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
            //TODO rewrite role
            return new AuthResponse(generateAccessToken(authRequest.getLogin(), UserRole.USER),
                    generateRefreshToken(authRequest.getLogin(), UserRole.USER));
        }
        throw new IllegalStateException("User not found or password incorrect");
    }

    public String generateAccessToken(String login, UserRole role) {
        Instant now = Instant.now();
        Instant accessExpirationInstant = now.plus(accessTokenTtl);
        Date accessExpiration = Date.from(accessExpirationInstant);
        return jwtTokenUtil.generateToken(login, accessExpiration, role);
    }

    public String generateRefreshToken(String login, UserRole role) {
        Instant now = Instant.now();
        Instant accessExpirationInstant = now.plus(refreshTokenTtl);
        Date accessExpiration = Date.from(accessExpirationInstant);
        return jwtTokenUtil.generateToken(login, accessExpiration, role);
    }
}
