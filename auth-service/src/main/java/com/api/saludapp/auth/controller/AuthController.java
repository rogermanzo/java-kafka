package com.api.saludapp.auth.controller;

import com.api.saludapp.auth.model.LoginRequest;
import com.api.saludapp.auth.model.LoginResponseInterface;
import com.api.saludapp.auth.model.LoginSuccessDto;
import com.api.saludapp.auth.model.LoginErrorDto;
import com.api.saludapp.auth.model.TokenValidationResponse;
import com.api.saludapp.auth.model.UserInfoResponse;
import com.api.saludapp.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponseInterface>> login(@RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest)
                .map(loginResponse -> ResponseEntity.ok((LoginResponseInterface) LoginSuccessDto.create(
                    loginResponse.getToken(),
                    loginResponse.getType(),
                    loginResponse.getUserId(),
                    loginResponse.getEmail(),
                    loginResponse.getName()
                )))
                .onErrorResume(throwable -> 
                    Mono.just(ResponseEntity.status(401)
                            .body((LoginResponseInterface) LoginErrorDto.create("Invalid credentials", "AUTHENTICATION_FAILED", 401))));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestHeader("Authorization") String token) {
        String cleanToken = extractToken(token);
        TokenValidationResponse response = authService.validateTokenResponse(cleanToken);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> getUserFromToken(@RequestHeader("Authorization") String token) {
        String cleanToken = extractToken(token);
        UserInfoResponse response = authService.getUserInfoResponse(cleanToken);
        
        if (response.getEmail() == null) {
            return ResponseEntity.status(401).body(response);
        }
        
        return ResponseEntity.ok(response);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return authorizationHeader;
    }
}
