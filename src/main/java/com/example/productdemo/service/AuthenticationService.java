package com.example.productdemo.service;

import com.example.productdemo.dao.UserDao;
import com.example.productdemo.entity.UserEntity;
import com.example.productdemo.request.AuthRequest;
import com.example.productdemo.response.AuthResponse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationService {
    private final UserDao userDao;
    private final SecurityService securityService;

    public AuthenticationService(UserDao userDao, SecurityService securityService) {
        this.userDao = userDao;
        this.securityService = securityService;
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
            return new AuthResponse(generateAccessToken(authRequest.getLogin(), userEntity.get().getRole()),
                    generateRefreshToken(authRequest.getLogin(), userEntity.get().getRole()));
        }
        throw new IllegalStateException("User not found or password incorrect");
    }

    public String generateAccessToken(String login, Short role) {
        return "";
    }

    public String generateRefreshToken(String login, Short role) {
        return "";
    }
}
