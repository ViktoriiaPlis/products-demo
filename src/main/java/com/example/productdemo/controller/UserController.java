package com.example.productdemo.controller;

import com.example.productdemo.request.ProductRequest;
import com.example.productdemo.request.UserRequest;
import com.example.productdemo.response.ProductResponse;
import com.example.productdemo.response.UserResponse;
import com.example.productdemo.service.ProductService;
import com.example.productdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@RestController
@Validated
@Tag(name = "user")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @Operation(summary = "Создание пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление пользователя")
    public UserResponse updateUser(@PathVariable UUID id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }
}
