package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Manter para validação a nível de entidade, se desejado
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não mais necessário
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "work_hours", indexes = {
    @Index(name = "idx_wh_employee_id", columnList = "employee_id"),
    @Index(name = "idx_wh_project_id", columnList = "project_id"),
    @Index(name = "idx_wh_cost_center_id", columnList = "cost_center_id"),
    @Index(name = "idx_wh_work_date", columnList = "work_date"),
    // Constraint para garantir que um funcionário não tenha múltiplas entradas para o mesmo dia, projeto e centro de custo
    // Isso pode ser muito restritivo. Considere se é realmente necessário ou se a descrição diferencia.
    // @UniqueConstraint(name = "uk_wh_emp_date_proj_cc", columnNames = {"employee_id", "work_date", "project_id", "cost_center_id"})
})
public class WorkHoursEntity extends AuditableBaseEntity {

    private static final Logger log = LoggerFactory.getLogger(WorkHoursEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <<< ESTRATÉGIA UUID
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @NotNull // Garante que a FK não seja nula no banco, além da anotação optional=false no @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false, foreignKey = @ForeignKey(name = "fk_workhours_employee"))
    private EmployeeEntity employee;

    @Column(name = "project_id") // Mantido como Long (ou UUID se o project-service usar UUID para Project)
    private Long projectId;

    @Column(name = "cost_center_id") // Mantido como Long (ou UUID se o cost-center-service usar UUID)
    private Long costCenterId;

    @NotNull
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @NotNull
    @Column(name = "hours_worked", nullable = false, precision = 5, scale = 2)
    private BigDecimal hoursWorked;

    @Column(name = "calculated_cost", precision = 10, scale = 2)
    private BigDecimal calculatedCost;

    @Column(name = "description", length = 500)
    private String description;

    @PrePersist
    @PreUpdate
    private void validateAndCalculateCost() {
        UUID currentIdLog = this.getId() != null ? this.getId() : UUID.fromString("0-0-0-0-0"); // Placeholder for new entities in log

        if (projectId == null && costCenterId == null) {
            log.error("Validation failed for WorkHours (ID: {}): Must be allocated to either a project or a cost center.", currentIdLog);
            throw new IllegalStateException("Work hours must be allocated to either a project or a cost center.");
        }
        if (projectId != null && costCenterId != null) {
            log.error("Validation failed for WorkHours (ID: {}): Cannot be allocated to both a project and a cost center.", currentIdLog);
            throw new IllegalStateException("Work hours cannot be allocated to both a project and a cost center simultaneously.");
        }

        if (hoursWorked == null || hoursWorked.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Validation failed for WorkHours (ID: {}): Hours worked must be positive. Found: {}", currentIdLog, hoursWorked);
            throw new IllegalArgumentException("Hours worked must be positive.");
        }

        if (employee != null && employee.getHourlyRate() != null && employee.getHourlyRate().compareTo(BigDecimal.ZERO) > 0) {
            this.calculatedCost = hoursWorked.multiply(employee.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);
            log.debug("Calculated cost for WorkHours (ID: {}) as: {}", currentIdLog, this.calculatedCost);
        } else {
            this.calculatedCost = BigDecimal.ZERO;
            String employeeIdLog = (employee != null && employee.getId() != null) ? employee.getId().toString() : "UNKNOWN_OR_NULL";
            BigDecimal hourlyRateLog = (employee != null) ? employee.getHourlyRate() : null;
            log.warn("Cannot calculate cost for WorkHours (ID: {}): Employee (ID: {}) or its hourly rate (Rate: {}) is null or not positive.",
                    currentIdLog, employeeIdLog, hourlyRateLog);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkHoursEntity that = (WorkHoursEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}