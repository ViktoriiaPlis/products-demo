package com.example.productdemo.controller;

import com.example.productdemo.request.AuthRequest;
import com.example.productdemo.response.AuthResponse;
import com.example.productdemo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@Tag(name = "auth")
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
    public AuthResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return authService.login(authRequest);
    }

//    @PostMapping("/token")
//    @Operation(summary = "токен")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
//    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
//        // final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
//        return ResponseEntity.ok(new JwtResponse("", ""));
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
//        // final JwtResponse token = authService.refresh(request.getRefreshToken());
//        return ResponseEntity.ok(new JwtResponse("", ""));
//    }
}
