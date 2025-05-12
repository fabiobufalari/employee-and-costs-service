package com.bufalari.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Importar Schema
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar construtor padrão
import lombok.AllArgsConstructor; // Adicionar construtor com todos os args

import java.time.LocalDate;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO for representing the allocation history of an employee (using UUID).
 * DTO para representar o histórico de alocação de um funcionário (usando UUID).
 */
@Data
@NoArgsConstructor // Adicionado
@AllArgsConstructor // Adicionado
public class AllocationHistoryDTO {

    @Schema(description = "Unique identifier (UUID) of the allocation record", example = "f1f2f3f4-a1a2-b3b4-c5c6-d7d8d9d0d1d2", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    /**
     * UUID of the allocated employee.
     * UUID do funcionário alocado.
     */
    @NotNull(message = "Employee ID is required / ID do funcionário é obrigatório")
    @Schema(description = "UUID of the allocated employee", example = "e1e2e3e4-f5f6-7890-1234-567890abcdef", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID employeeId; // <<<--- Changed to UUID

    /**
     * ID of the project the employee is allocated to (assuming project ID remains Long).
     * ID do projeto ao qual o funcionário está alocado (assumindo que ID do projeto continua Long).
     */
    @NotNull(message = "Project ID is required / ID do projeto é obrigatório")
    @Schema(description = "ID of the project (assuming Long)", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long projectId; // Mantém Long

    /**
     * Start date of the allocation.
     * Data de início da alocação.
     */
    @NotNull(message = "Start date is required / Data de início é obrigatória")
    @Schema(description = "Start date of the allocation", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate startDate;

    /**
     * End date of the allocation (optional, null if active).
     * Data de término da alocação (opcional, nulo se ativa).
     */
    @Schema(description = "End date of the allocation (null if currently active)", example = "2024-06-30", nullable = true)
    private LocalDate endDate;

    /**
     * Optional description.
     * Descrição opcional.
     */
    @Schema(description = "Optional description or notes about the allocation", example = "Lead Engineer for Phase 1", nullable = true)
    private String description;
}