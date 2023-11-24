package com.example.productdemo.service;

import com.example.productdemo.dao.UserDao;
import com.example.productdemo.entity.UserEntity;
import com.example.productdemo.request.UserRequest;
import com.example.productdemo.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserService {
    private final UserDao userDao;
    private final ObjectMapper objectMapper;
    
    private final SecurityService securityService;

    public UserService(UserDao userDao, ObjectMapper objectMapper, SecurityService securityService) {
        this.userDao = userDao;
        this.objectMapper = objectMapper;
        this.securityService = securityService;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        Optional<UserEntity> existingUser = userDao.findByLoginAndDeletedAtIsNull(userRequest.getLogin());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Login already existing" + userRequest.getLogin());
        }
        String salt = securityService.generateSalt();
        UserEntity user = new UserEntity(
                userRequest.getLogin(),
                securityService.generatePasswordHash(userRequest.getPassword(), salt),
                userRequest.getRole(),
                salt
        );
        UserEntity savedUser = userDao.save(user);

        return objectMapper.convertValue(savedUser, UserResponse.class);
    }


    @Transactional
    public void deleteUser(UUID id) {
        Optional<UserEntity> user = userDao.findById(id);
        if (user.isPresent()) {
            user.get().setDeletedAt(Instant.now());
            userDao.save(user.get());
        } else {
            throw new IllegalStateException("User not found : id" + id);
        }
    }

    @Transactional
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        Optional<UserEntity> user = userDao.findById(id);
        if (user.isPresent()) {
            String salt = securityService.generateSalt();
            user.get().setLogin(userRequest.getLogin());
            user.get().setRole(userRequest.getRole());
            user.get().setHash(securityService.generatePasswordHash(userRequest.getPassword(), salt));
            user.get().setSalt(salt);
            userDao.save(user.get());
            return objectMapper.convertValue(user, UserResponse.class);
        } else {
            throw new IllegalStateException("User not found : id" + id);
        }
    }
}
