package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid; // Para validar lista de permissões
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO representing a Role (using UUID).
 * DTO representando um Papel/Função (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @Schema(description = "Unique identifier (UUID) of the role", example = "role_admin_uuid", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    @Schema(description = "Name of the role", example = "ROLE_ADMIN")
    private String name; // Ex: ROLE_ADMIN, ROLE_MANAGER

    @Schema(description = "List of permissions associated with this role")
    @Valid // Validar cada PermissionDTO na lista (se PermissionDTO tiver validações)
    private List<PermissionDTO> permissions; // PermissionDTO também deve usar UUID
}