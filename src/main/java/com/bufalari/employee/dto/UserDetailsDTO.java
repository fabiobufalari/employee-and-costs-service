package com.bufalari.employee.dto;

import lombok.Data;

import java.util.List;

/**
 * DTO para os detalhes do usuário retornados pelo serviço de autenticação.
 * DTO for user details returned by the authentication service.
 */
@Data
public class UserDetailsDTO {
    private String username;
    private String password;
    private List<String> roles;
}