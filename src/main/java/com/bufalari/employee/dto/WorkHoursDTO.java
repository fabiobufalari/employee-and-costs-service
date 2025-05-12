package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Importar
import jakarta.validation.constraints.DecimalMin; // Para validar horas
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar

import java.math.BigDecimal; // Usar BigDecimal para horas e custos
import java.time.LocalDate;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO para representar as horas trabalhadas por um funcionário (usando UUID).
 * DTO representing the hours worked by an employee (using UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkHoursDTO {

    @Schema(description = "Unique identifier (UUID) of the work hours record", example = "wh_uuid_123", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    // Employee ID é definido pelo path no POST, mas pode ser útil no GET/Response
    @NotNull(message = "{workHours.employeeId.required}")
    @Schema(description = "UUID of the employee who worked", example = "e1e2e3e4-f5f6-7890-1234-567890abcdef", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID employeeId; // <<<--- Changed to UUID

    @Schema(description = "ID of the project (Long) these hours are for", example = "101", nullable = true)
    private Long projectId; // <<<--- Renomeado e mantido Long (ou UUID se Project usar)

    @Schema(description = "ID of the cost center (Long) these hours are for", example = "202", nullable = true)
    private Long costCenterId; // <<<--- Adicionado e mantido Long (ou UUID se CostCenter usar)

    @NotNull(message = "{workHours.workDate.required}")
    @Schema(description = "Date the work was performed", example = "2024-05-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate workDate;

    @NotNull(message = "{workHours.hoursWorked.required}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{workHours.hoursWorked.min}") // Horas devem ser positivas
    @Schema(description = "Number of hours worked", example = "7.5", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal hoursWorked; // <<<--- Usar BigDecimal para precisão

    @Schema(description = "Calculated cost for these hours (hours * rate)", example = "266.25", readOnly = true)
    private BigDecimal calculatedCost; // <<<--- Usar BigDecimal

    @Schema(description = "Optional description of the work performed", example = "Concrete pouring - Section A", nullable = true)
    private String description; // <<<--- Adicionado

    // O campo constructionId foi removido/renomeado para projectId/costCenterId
}