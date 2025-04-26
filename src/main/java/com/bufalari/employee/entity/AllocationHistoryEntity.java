// Path: employee-and-costs-service/src/main/java/com/bufalari/employee/entity/AllocationHistoryEntity.java
package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity; // Import the base class
import jakarta.persistence.*;
import lombok.AllArgsConstructor; // Add missing Lombok annotations
import lombok.Getter;          // Add missing Lombok annotations
import lombok.NoArgsConstructor; // Add missing Lombok annotations
import lombok.Setter;          // Add missing Lombok annotations

import java.time.LocalDate;

/**
 * Entity representing the allocation history of an employee to construction projects.
 * Includes auditing fields.
 * Entidade que representa o histórico de alocação de um funcionário em obras.
 * Inclui campos de auditoria.
 */
@Entity
@Getter                 // Use individual annotations
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "allocation_history")
public class AllocationHistoryEntity extends AuditableBaseEntity { // Inherit from AuditableBaseEntity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The employee who is allocated.
     * O funcionário que está alocado.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Allocation must belong to an employee
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    /**
     * Identifier of the project to which the employee is allocated.
     * Identificador do projeto ao qual o funcionário está alocado.
     */
    // Rename field and column / Renomeia campo e coluna
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * The start date of the allocation period.
     * A data de início do período de alocação.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * The end date of the allocation period (null if currently active).
     * A data de término do período de alocação (nulo se atualmente ativo).
     */
    @Column(name = "end_date")
    private LocalDate endDate;

    /**
     * Optional description or justification for the allocation.
     * Descrição ou justificativa opcional para a alocação.
     */
    @Column(name = "description", length = 500)
    private String description;
}