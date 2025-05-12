package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Importar
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; // Usar NotBlank
import jakarta.validation.constraints.NotNull; // Usar NotBlank para Strings
import jakarta.validation.constraints.Size; // Importar Size
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar

import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO for Manager or Responsible person information (using UUID).
 * DTO para informações de Gerente ou Responsável (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponsibleDTO {

    @Schema(description = "Unique identifier (UUID) of the manager/responsible", example = "a1b2c3d4-a1b2-c3d4-d5e6-f7a8b9c0d1e2", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    @NotBlank(message = "{managerResponsible.code.required}")
    @Size(max = 50, message = "{managerResponsible.code.size}")
    @Schema(description = "Internal code or identifier", example = "MGR-007", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @NotBlank(message = "{managerResponsible.name.required}")
    @Size(max = 255, message = "{managerResponsible.name.size}")
    @Schema(description = "Full name", example = "Ana Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "{managerResponsible.position.required}")
    @Size(max = 100, message = "{managerResponsible.position.size}")
    @Schema(description = "Job position", example = "Gerente de Projetos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String position;

    @NotBlank(message = "{managerResponsible.phone.required}")
    @Size(max = 50, message = "{managerResponsible.phone.size}")
    @Schema(description = "Contact phone number", example = "+55 21 98877-6655", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @NotBlank(message = "{managerResponsible.email.required}") // Email não pode ser branco
    @Email(message = "{managerResponsible.email.invalid}")
    @Size(max = 255, message = "{managerResponsible.email.size}")
    @Schema(description = "Contact email address", example = "ana.souza@company.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}