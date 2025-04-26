
package com.bufalari.employee.entity;

import com.bufalari.employee.auditing.AuditableBaseEntity;
import com.bufalari.employee.enums.EmploymentType; // Import the enum
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing an employee, including cost information and auditing.
 * Entidade que representa um funcionário, incluindo informações de custo e auditoria.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
public class EmployeeEntity extends AuditableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier linking to the UserEntity in the authentication service.
     * Identificador único que liga à UserEntity no serviço de autenticação.
     */
    @Column(name = "user_id", unique = true) // Make it unique if one employee maps to one user
    private UUID userId; // Can be null if employee is not a system user

    /**
     * Employee's first name.
     * Primeiro nome do funcionário.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Employee's last name.
     * Sobrenome do funcionário.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Social Insurance Number (Canada) or equivalent national identifier.
     * Número de Seguro Social (Canadá) ou identificador nacional equivalente.
     */
    @Column(unique = true) // Often unique
    private String socialInsuranceNumber;

    /**
     * Employee's date of birth.
     * Data de nascimento do funcionário.
     */
    private LocalDate birthDate;

    /**
     * Date the employee was hired.
     * Data em que o funcionário foi contratado.
     */
    @Column(nullable = false)
    private LocalDate hireDate;

    /**
     * Date the employee's employment ended (if applicable).
     * Data em que o emprego do funcionário terminou (se aplicável).
     */
    private LocalDate terminationDate;

    /**
     * Employee's address details.
     * Detalhes do endereço do funcionário.
     */
    @Embedded
    private AddressEntity address;

    /**
     * Gross salary amount (can be annual, monthly, based on payFrequency).
     * Valor do salário bruto (pode ser anual, mensal, baseado na payFrequency).
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    /**
     * Frequency of payment (e.g., HOURLY, WEEKLY, BI_WEEKLY, MONTHLY).
     * Frequência de pagamento (ex: POR_HORA, SEMANAL, QUINZENAL, MENSAL).
     */
    @Column(length = 20) // Example length
    private String payFrequency;

    /**
     * Hourly rate, especially for non-salaried employees or for cost calculation.
     * Taxa horária, especialmente para funcionários não assalariados ou para cálculo de custo.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    /**
     * Estimated monthly cost of benefits for the employee.
     * Custo mensal estimado dos benefícios para o funcionário.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal benefitsCostMonthly;

     /**
     * Type of employment (e.g., Full-time, Part-time, Contractor).
     * Tipo de emprego (ex: Tempo Integral, Meio Período, Contratado).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20) // Ensure length accommodates enum names
    private EmploymentType employmentType;


    // --- Relationships ---

    /**
     * Work hours recorded for this employee.
     * Horas trabalhadas registradas para este funcionário.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WorkHoursEntity> workHours;

    /**
     * History of project allocations for this employee.
     * Histórico de alocações em projetos para este funcionário.
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AllocationHistoryEntity> allocationHistory;

    // Note: Removed CompanyEntity, RoleEntity, ContactEntity relationships
    // Nota: Removidos relacionamentos com CompanyEntity, RoleEntity, ContactEntity
}