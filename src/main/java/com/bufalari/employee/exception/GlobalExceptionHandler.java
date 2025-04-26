package com.bufalari.employee.exception;

import jakarta.servlet.http.HttpServletRequest; // Correct import for Jakarta EE
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for the Employee and Costs Service API.
 * Catches specific exceptions and formats them into a standardized ErrorResponse.
 * Manipulador global de exceções para a API do Serviço de Funcionários e Custos.
 * Captura exceções específicas e as formata em um ErrorResponse padronizado.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Standard structure for API error responses.
     * Estrutura padrão para respostas de erro da API.
     */
    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            Object details // For validation errors or other details / Para erros de validação ou outros detalhes
    ) {}

    // --- 400 Bad Request ---

    /**
     * Handles bean validation errors (@Valid).
     * Trata erros de validação de bean (@Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        String message = "Validation failed. / Falha na validação.";
        log.warn("Validation error: {} - Path: {}", errors, request.getRequestURI());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI(), errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles constraint violations (e.g., path variables, request parameters).
     * Trata violações de constraint (ex: variáveis de path, parâmetros de requisição).
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        String errors = ex.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining("; "));
        String message = "Constraint violation: " + errors + " / Violação de restrição: " + errors;
        log.warn("Constraint violation: {} - Path: {}", errors, request.getRequestURI());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles general bad request errors like illegal arguments or state.
     * Trata erros gerais de bad request como argumentos ou estado ilegal.
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleBadDataExceptions(Exception ex, HttpServletRequest request) {
         String message = "Invalid request data or state: " + ex.getMessage() + " / Dados da requisição ou estado inválido: " + ex.getMessage();
         log.warn("Bad request data: {} - Path: {}", ex.getMessage(), request.getRequestURI());
         ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI(), null);
         return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // --- 401 Unauthorized ---

    /**
     * Handles authentication errors.
     * Trata erros de autenticação.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        String message = "Authentication failed: " + ex.getMessage() + " / Falha na autenticação: " + ex.getMessage();
        log.warn("Authentication failure - Path: {}", request.getRequestURI(), ex);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, request.getRequestURI(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // --- 403 Forbidden ---

    /**
     * Handles access denied errors (e.g., insufficient roles/permissions).
     * Trata erros de acesso negado (ex: roles/permissões insuficientes).
     */
     @ExceptionHandler(AccessDeniedException.class)
     public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
         String message = "Access Denied: You do not have permission for this action. / Acesso Negado: Você não tem permissão para esta ação.";
         log.warn("Access denied: User does not have permission for {} - Path: {}", request.getMethod(), request.getRequestURI());
         ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message, request.getRequestURI(), null);
         return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
     }

    // --- 404 Not Found ---
    // Consider creating a specific ResourceNotFoundException or using IllegalArgumentException

    // --- 500 Internal Server Error ---

    /**
     * Handles unexpected errors.
     * Trata erros inesperados.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        String message = "An unexpected internal error occurred. / Ocorreu um erro interno inesperado.";
        log.error("Unexpected internal server error - Path: {}", request.getRequestURI(), ex);
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), message, request.getRequestURI(), ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}