package com.example.productdemo.request;

import com.example.productdemo.model.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Модель создание пользователя")
public class UserRequest implements Serializable {
    @Schema(description = "Логин")
    @NotBlank
    private String login;

    @Schema(description = "Роль")
    @NotBlank
    private UserRole userRole;

    @Schema(description = "Пароль")
    @NotBlank
    private String password;
}
