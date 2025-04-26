package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import com.bufalari.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity representing the hours worked by an employee, linked to a project or cost center.
 * Entidade que representa as horas trabalhadas por um funcionário, vinculada a um projeto ou centro de custo.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "work_hours")
public class WorkHoursEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The employee who worked these hours.
     * O funcionário que trabalhou estas horas.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Must belong to an employee
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    /**
     * Identifier for the construction project these hours are allocated to. Null if allocated to a cost center.
     * Identificador para o projeto de construção ao qual estas horas são alocadas. Nulo se alocado a um centro de custo.
     */
    @Column(name = "project_id") // Renamed from constructionId
    private Long projectId;

    /**
     * Identifier for the cost center these hours are allocated to (e.g., administrative overhead). Null if allocated to a project.
     * Identificador para o centro de custo ao qual estas horas são alocadas (ex: despesas administrativas). Nulo se alocado a um projeto.
     */
    @Column(name = "cost_center_id")
    private Long costCenterId;

    /**
     * The date the work was performed.
     * A data em que o trabalho foi realizado.
     */
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    /**
     * The number of hours worked on this date for the specified project/cost center.
     * O número de horas trabalhadas nesta data para o projeto/centro de custo especificado.
     */
    @Column(name = "hours_worked", nullable = false, precision = 5, scale = 2) // Allow for partial hours like 7.5
    private BigDecimal hoursWorked;

    /**
     * The calculated cost for these hours (hoursWorked * employee.hourlyRate). Can be calculated on save/update.
     * O custo calculado para estas horas (horasTrabalhadas * funcionario.taxaHoraria). Pode ser calculado ao salvar/atualizar.
     */
    @Column(name = "calculated_cost", precision = 10, scale = 2)
    private BigDecimal calculatedCost;

    /**
     * Optional description or note about the work performed.
     * Descrição ou nota opcional sobre o trabalho realizado.
     */
    @Column(name = "description", length = 500)
    private String description;


    // --- Validation Logic (Example using @PrePersist/@PreUpdate) ---
    @PrePersist
    @PreUpdate
    private void validateAllocationAndCalculateCost() {
        // Ensure either projectId or costCenterId is set, but not both
        // Garante que ou projectId ou costCenterId esteja definido, mas não ambos
        if (projectId == null && costCenterId == null) {
            throw new IllegalStateException("Work hours must be allocated to either a project or a cost center.");
        }
        if (projectId != null && costCenterId != null) {
            throw new IllegalStateException("Work hours cannot be allocated to both a project and a cost center simultaneously.");
        }

        // Calculate cost if employee and hourly rate are available
        // Calcula o custo se o funcionário e a taxa horária estiverem disponíveis
        if (employee != null && employee.getHourlyRate() != null && hoursWorked != null) {
            this.calculatedCost = hoursWorked.multiply(employee.getHourlyRate());
        } else {
            this.calculatedCost = BigDecimal.ZERO; // Or null, depending on requirements
        }
    }
}