package com.api.saludapp.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private boolean success;
    private String message;
    private String error;
    private int status;
    
    public static ErrorResponse authenticationFailed(String message) {
        return new ErrorResponse(false, message, "AUTHENTICATION_FAILED", 401);
    }
    
    public static ErrorResponse userNotFound(String message) {
        return new ErrorResponse(false, message, "USER_NOT_FOUND", 404);
    }
}
