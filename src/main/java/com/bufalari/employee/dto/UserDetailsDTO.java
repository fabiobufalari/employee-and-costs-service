package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Importar
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO local para os detalhes do usuário recebidos do serviço de autenticação.
 * Local DTO for user details received from the authentication service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO {

    @Schema(description = "User's unique identifier (UUID)")
    private UUID id; // <<<--- Adicionado ID

    @Schema(description = "Username")
    private String username;

    // Senha NUNCA deve ser incluída
    // private String password;

    @Schema(description = "List of user roles")
    private List<String> roles; // <<<--- Adicionada lista de roles
}