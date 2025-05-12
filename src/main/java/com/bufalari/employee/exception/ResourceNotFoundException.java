package com.bufalari.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a requested resource is not found.
 * Maps to HTTP 404 Not Found status code.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Isso faz o Spring retornar 404 automaticamente
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}