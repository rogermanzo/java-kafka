package com.api.saludapp.auth.service;

import com.api.saludapp.auth.model.LoginRequest;
import com.api.saludapp.auth.model.LoginSuccessDto;
import com.api.saludapp.auth.model.TokenValidationResponse;
import com.api.saludapp.auth.model.UserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    @Value("${user-service.url}")
    private String userServiceUrl;

    private final WebClient webClient;
    private final Map<String, String> tokenEmailMap = new ConcurrentHashMap<>();

    public AuthService() {
        this.webClient = WebClient.builder().build();
    }

    // Verificar credenciales con el user-service
    public Mono<LoginSuccessDto> authenticate(LoginRequest loginRequest) {
        return webClient.get()
                .uri(userServiceUrl + "/api/users/email/" + loginRequest.getEmail())
                .retrieve()
                .bodyToMono(Map.class)
                .map(userData -> {
                    if (userData == null || userData.isEmpty()) {
                        throw new RuntimeException("User not found");
                    }
                    
                    String token = "token_" + System.currentTimeMillis();
                    tokenEmailMap.put(token, loginRequest.getEmail());
                    
                    return LoginSuccessDto.create(
                        token,
                        "Bearer",
                        Long.valueOf(userData.get("id").toString()),
                        loginRequest.getEmail(),
                        userData.get("name").toString()
                    );
                })
                .onErrorMap(throwable -> new RuntimeException("Authentication failed: " + throwable.getMessage()));
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith("token_");
    }

    public String getEmailFromToken(String token) {
        if (token != null && token.startsWith("token_")) {
            String email = tokenEmailMap.get(token);
            if (email == null) {
                return null;
            }
            return email;
        }
        return null;
    }

    // Valida un token y retorna la respuesta completa
    public TokenValidationResponse validateTokenResponse(String token) {
        boolean isValid = validateToken(token);
        String message = isValid ? "Token is valid" : "Token is invalid";
        return new TokenValidationResponse(isValid, message);
    }

    // Obtiene información del usuario desde el token y retorna la respuesta completa
    public UserInfoResponse getUserInfoResponse(String token) {
        String email = getEmailFromToken(token);
        
        if (email == null) {
            return new UserInfoResponse(null, "Token not found or expired");
        }
        
        return new UserInfoResponse(email, "User found");
    }
}