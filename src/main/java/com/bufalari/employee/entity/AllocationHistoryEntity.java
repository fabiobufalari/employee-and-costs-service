package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import jakarta.persistence.*;
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não mais necessário

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "allocation_history", indexes = {
    @Index(name = "idx_alloc_employee_id", columnList = "employee_id"),
    @Index(name = "idx_alloc_project_id", columnList = "project_id"),
    @Index(name = "idx_alloc_start_date", columnList = "start_date"),
    @Index(name = "idx_alloc_end_date", columnList = "end_date")
})
public class AllocationHistoryEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <<< ESTRATÉGIA UUID
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_alloc_history_employee"))
    private EmployeeEntity employee;

    @Column(name = "project_id", nullable = false) // Mantido como Long
    private Long projectId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "description", length = 500)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllocationHistoryEntity that = (AllocationHistoryEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}