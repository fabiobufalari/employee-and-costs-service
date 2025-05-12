package com.bufalari.employee.dto;

import com.bufalari.employee.enums.EmploymentType; // Importar Enum
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid; // Para validar AddressDTO
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicionar
import lombok.AllArgsConstructor; // Adicionar
import lombok.Builder; // Adicionar

import java.math.BigDecimal; // Importar BigDecimal
import java.time.LocalDate;
// import java.util.List; // Não usado diretamente aqui
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * DTO for Employee data transfer (using UUID).
 * DTO para transferência de dados de Funcionário (usando UUID).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Adicionado Builder
public class EmployeeDTO {

    @Schema(description = "Unique identifier (UUID) of the employee record", example = "e1e2e3e4-f5f6-7890-1234-567890abcdef", readOnly = true)
    private UUID id; // <<<--- Changed to UUID

    @Schema(description = "Unique identifier (UUID) linking to the user in the authentication service (if applicable)", example = "f0e9d8c7-b6a5-4321-fedc-ba9876543210", nullable = true)
    private UUID userId; // <<<--- Changed to UUID (pode ser nulo se não for usuário do sistema)

    @NotBlank(message = "{employee.firstName.required}")
    @Size(max = 50, message = "{employee.firstName.size}")
    @Schema(description = "Employee's first name", example = "Carlos", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "{employee.lastName.required}")
    @Size(max = 100, message = "{employee.lastName.size}")
    @Schema(description = "Employee's last name", example = "Pereira", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @Size(max = 50, message = "{employee.sin.size}") // Ajustar tamanho
    @Schema(description = "Social Insurance Number or national identifier", example = "987-654-321", nullable = true)
    private String socialInsuranceNumber; // Pode ser nulo ou opcional dependendo da região/regras

    @Past(message = "{employee.birthDate.past}")
    @Schema(description = "Employee's date of birth", example = "1985-07-22", nullable = true)
    private LocalDate birthDate; // Pode ser nulo

    @NotNull(message = "{employee.hireDate.required}")
    @PastOrPresent(message = "{employee.hireDate.pastOrPresent}") // Não pode ser no futuro
    @Schema(description = "Date the employee was hired", example = "2022-01-10", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate hireDate;

    @PastOrPresent(message = "{employee.terminationDate.pastOrPresent}")
    @Schema(description = "Date the employee's employment ended (if applicable)", example = "2024-12-31", nullable = true)
    private LocalDate terminationDate; // Adicionado

    @NotNull(message = "{employee.employmentType.required}")
    @Schema(description = "Type of employment", example = "FULL_TIME", requiredMode = Schema.RequiredMode.REQUIRED)
    private EmploymentType employmentType; // Adicionado

    @Valid // Validar o DTO de endereço aninhado
    @Schema(description = "Employee's address", nullable = true)
    private AddressDTO address; // Pode ser nulo

    @Schema(description = "Gross salary amount", example = "60000.00", nullable = true)
    private BigDecimal salary; // Pode ser nulo (ex: horista)

    @Schema(description = "Hourly rate", example = "35.50", nullable = true)
    private BigDecimal hourlyRate; // Adicionado, pode ser nulo (ex: assalariado)

     @Schema(description = "Estimated monthly cost of benefits", example = "500.00", nullable = true)
    private BigDecimal benefitsCostMonthly; // Adicionado, pode ser nulo

    @Size(max = 20, message = "{employee.payFrequency.size}")
    @Schema(description = "Frequency of payment (e.g., HOURLY, WEEKLY, MONTHLY)", example = "MONTHLY", nullable = true)
    private String payFrequency; // Pode ser nulo ou ter valores padrão

    // RoleDTO foi removido da entidade original, remover daqui também se não for usado
    // @Schema(description = "Role assigned to the employee", nullable = true)
    // private RoleDTO role;
}