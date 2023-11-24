package com.example.productdemo.controller;

import com.example.productdemo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
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

//    @PostMapping("/login")
//    @Operation(summary = "Аутентификация пользователя")
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Операция успешно выполнена")})
//    public ResponseEntity<JwtResponse> login(@RequestBody @Valid JwtRequest authRequest) {
//        String token = authService.login(authRequest);
//        return ResponseEntity.ok();
//    }

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
