package com.api.saludapp.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginSuccessDto implements LoginResponseInterface {
    private boolean success;
    private String message;
    private String token;
    private String type;
    private Long userId;
    private String email;
    private String name;
    
    public static LoginSuccessDto create(String token, String type, Long userId, String email, String name) {
        return new LoginSuccessDto(true, "Authentication successful", token, type, userId, email, name);
    }
}
