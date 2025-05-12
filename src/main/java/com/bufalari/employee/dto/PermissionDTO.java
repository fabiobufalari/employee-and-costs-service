package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank; // Para validação do nome
import jakarta.validation.constraints.Size;     // Para validação do nome
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder; // Adicionar Builder

import java.util.UUID;

/**
 * DTO representing a permission (using UUID).
 * DTO representando uma permissão (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Adicionado para consistência
public class PermissionDTO {

    @Schema(description = "Unique identifier (UUID) of the permission", example = "perm_uuid_example_read_reports", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id; // ID da permissão

    @NotBlank(message = "{permission.name.required}")
    @Size(max = 100, message = "{permission.name.size}")
    @Schema(description = "Name or code of the permission", example = "REPORTS_READ_FINANCIAL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name; // Nome ou código da permissão (ex: REPORTS_READ, USERS_CREATE)

    @Size(max = 255, message = "{permission.description.size}")
    @Schema(description = "Description of what the permission allows", example = "Allows reading financial reports", nullable = true)
    private String description; // Descrição (opcional)
}