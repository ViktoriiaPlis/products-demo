package com.example.productdemo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthRequest {
    @Schema(description = "Логин")
    @NotBlank
    private String login;

    @Schema(description = "Пароль")
    @NotBlank
    private String password;

}
