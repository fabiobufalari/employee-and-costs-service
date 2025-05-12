package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import com.bufalari.employee.enums.EmploymentType;
import jakarta.persistence.*;
import lombok.*;
// import org.hibernate.annotations.GenericGenerator; // Não mais necessário

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees", indexes = {
        @Index(name = "idx_employee_user_id", columnList = "user_id", unique = true), // user_id deve ser único se presente
        @Index(name = "idx_employee_sin", columnList = "socialInsuranceNumber", unique = true), // SIN deve ser único se presente
        @Index(name = "idx_employee_lastname_firstname", columnList = "lastName, firstName")
})
public class EmployeeEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // <<< ESTRATÉGIA UUID
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "user_id", unique = true, columnDefinition = "uuid") // Ligação com UserEntity do auth-service
    private UUID userId; // Pode ser nulo se o funcionário não for um usuário do sistema

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, length = 50)
    private String socialInsuranceNumber;

    private LocalDate birthDate;

    @Column(nullable = false)
    private LocalDate hireDate;

    private LocalDate terminationDate;

    @Embedded
    private AddressEntity address; // Entidade AddressEntity é embutida

    @Column(precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(length = 20)
    private String payFrequency;

    @Column(precision = 8, scale = 2)
    private BigDecimal hourlyRate;

    @Column(precision = 10, scale = 2)
    private BigDecimal benefitsCostMonthly;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30) // Aumentar tamanho para nomes de enum completos
    private EmploymentType employmentType;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WorkHoursEntity> workHours = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AllocationHistoryEntity> allocationHistory = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}