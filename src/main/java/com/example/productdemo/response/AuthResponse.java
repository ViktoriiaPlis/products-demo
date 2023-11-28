package com.example.productdemo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @Schema(description = "access token")
    @NotBlank
    private String accessToken;

    @Schema(description = "refresh token")
    @NotBlank
    private String refreshToken;
}
