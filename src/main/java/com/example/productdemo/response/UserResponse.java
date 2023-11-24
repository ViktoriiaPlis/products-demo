package com.example.productdemo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @Schema(description = "id пользователя")
    private UUID id;

    @Schema(description = "Логин пользователя")
    private String login;

    @Schema(description = "Роль")
    private String role;
}
