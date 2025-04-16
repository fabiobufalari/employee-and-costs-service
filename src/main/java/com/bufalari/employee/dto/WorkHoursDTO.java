package com.bufalari.employee.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO para representar as horas trabalhadas por um funcionário.
 * DTO representing the hours worked by an employee.
 */
@Data
public class WorkHoursDTO {

    private Long id;

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Construction ID is required")
    private Long constructionId;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    @NotNull(message = "Hours worked is required")
    private Double hoursWorked;
}