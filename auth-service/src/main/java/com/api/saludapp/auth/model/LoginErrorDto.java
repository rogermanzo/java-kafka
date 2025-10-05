package com.api.saludapp.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginErrorDto implements LoginResponseInterface {
    private boolean success;
    private String message;
    private String error;
    private int status;
    
    public static LoginErrorDto create(String message, String error, int status) {
        return new LoginErrorDto(false, message, error, status);
    }
}
