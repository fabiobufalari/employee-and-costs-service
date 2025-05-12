package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO for Department information (using UUID).
 * DTO para informações de Departamento (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @Schema(description = "Unique identifier (UUID) of the department", example = "d1d2d3d4-e5e6-f7f8-a9a0-b1b2b3b4b5b6", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    @Schema(description = "Name of the department", example = "Engenharia")
    private String name;

    @Schema(description = "List of sub-departments within this department")
    private List<SubDepartmentDTO> subDepartments; // SubDepartmentDTO também deve usar UUID
}