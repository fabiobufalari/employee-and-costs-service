package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank; // Para validação do nome
import jakarta.validation.constraints.NotNull;  // Para departmentId
import jakarta.validation.constraints.Size;     // Para validação do nome
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder; // Adicionar Builder

import java.util.UUID;

/**
 * DTO for SubDepartment information (using UUID).
 * DTO para informações de Subdepartamento (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Adicionado para consistência
public class SubDepartmentDTO {

    @Schema(description = "Unique identifier (UUID) of the sub-department", example = "sub_dep_eng_civil_uuid_example", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id; // ID do subdepartamento

    @NotBlank(message = "{subDepartment.name.required}")
    @Size(max = 100, message = "{subDepartment.name.size}")
    @Schema(description = "Name of the sub-department", example = "Estruturas", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotNull(message = "{subDepartment.departmentId.required}")
    @Schema(description = "UUID of the parent department", example = "d1d2d3d4-e5e6-f7f8-a9a0-b1b2b3b4b5b6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID departmentId; // ID do departamento pai

    // Opcional: Se você quiser incluir o nome do departamento pai na resposta
    // Este campo seria populado pelo serviço, não enviado na requisição de criação/update de subdepartamento
    @Schema(description = "Name of the parent department", example = "Engenharia", accessMode = Schema.AccessMode.READ_ONLY)
    private String departmentName;
}