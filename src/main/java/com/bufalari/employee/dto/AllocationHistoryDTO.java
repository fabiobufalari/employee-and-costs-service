package com.bufalari.employee.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO para representar o histórico de alocação de um funcionário.
 * DTO representing the allocation history of an employee.
 */
@Data
public class AllocationHistoryDTO {

    private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Construction ID is required")
    private Long constructionId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;
}