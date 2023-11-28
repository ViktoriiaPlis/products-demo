package com.example.productdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecurityService {

    private final String algorithm;

    public SecurityService(@Value("${app.algorithmPassword:SHA-256}") String algorithm) {
        this.algorithm = algorithm;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[48];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String generatePasswordHash(String password, String salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        byte[] bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(password)).array();
        messageDigest.update(bytes);
        return Base64.getEncoder().encodeToString(messageDigest.digest(Base64.getDecoder().decode(salt)));
    }
}
