// Path: employee-and-costs-service/src/main/java/com/bufalari/employee/dto/AllocationHistoryDTO.java
package com.bufalari.employee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for representing the allocation history of an employee.
 * DTO para representar o histórico de alocação de um funcionário.
 */
@Data
public class AllocationHistoryDTO {

    private Long id;

    /**
     * ID of the allocated employee.
     * ID do funcionário alocado.
     */
    @NotNull(message = "Employee ID is required / ID do funcionário é obrigatório")
    private Long employeeId;

    /**
     * ID of the project the employee is allocated to.
     * ID do projeto ao qual o funcionário está alocado.
     */
    // Rename field / Renomeia campo
    @NotNull(message = "Project ID is required / ID do projeto é obrigatório")
    private Long projectId;

    /**
     * Start date of the allocation.
     * Data de início da alocação.
     */
    @NotNull(message = "Start date is required / Data de início é obrigatória")
    private LocalDate startDate;

    /**
     * End date of the allocation (optional, null if active).
     * Data de término da alocação (opcional, nulo se ativa).
     */
    private LocalDate endDate;

    /**
     * Optional description.
     * Descrição opcional.
     */
    private String description;
}