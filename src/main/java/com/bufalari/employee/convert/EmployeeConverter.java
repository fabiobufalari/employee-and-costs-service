package com.bufalari.employee.convert;

import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor; // Usar Lombok para injeção
import org.springframework.stereotype.Component;

import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Converts between EmployeeEntity (with UUID ID) and EmployeeDTO (with UUID ID).
 * Conversor entre EmployeeEntity (com ID UUID) e EmployeeDTO (com ID UUID).
 */
@Component
@RequiredArgsConstructor // Injeta dependências finais (AddressConverter)
public class EmployeeConverter {

    private final AddressConverter addressConverter; // Injetado

    /**
     * Converts EmployeeEntity to EmployeeDTO.
     * Converte EmployeeEntity para EmployeeDTO.
     */
    public EmployeeDTO entityToDTO(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) return null;

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeEntity.getId()); // <<<--- UUID
        employeeDTO.setUserId(employeeEntity.getUserId()); // <<<--- UUID
        employeeDTO.setFirstName(employeeEntity.getFirstName());
        employeeDTO.setLastName(employeeEntity.getLastName());
        employeeDTO.setSocialInsuranceNumber(employeeEntity.getSocialInsuranceNumber());
        employeeDTO.setBirthDate(employeeEntity.getBirthDate());
        employeeDTO.setHireDate(employeeEntity.getHireDate());
        employeeDTO.setTerminationDate(employeeEntity.getTerminationDate()); // Adicionado
        employeeDTO.setEmploymentType(employeeEntity.getEmploymentType()); // Adicionado

        // Usa o AddressConverter injetado
        employeeDTO.setAddress(addressConverter.entityToDTO(employeeEntity.getAddress()));

        employeeDTO.setSalary(employeeEntity.getSalary());
        employeeDTO.setHourlyRate(employeeEntity.getHourlyRate()); // Adicionado
        employeeDTO.setBenefitsCostMonthly(employeeEntity.getBenefitsCostMonthly()); // Adicionado
        employeeDTO.setPayFrequency(employeeEntity.getPayFrequency());

        // Mapear RoleDTO, WorkHoursDTO, AllocationHistoryDTO se necessário (geralmente em endpoints específicos)
        // employeeDTO.setRole(roleConverter.entityToDTO(employeeEntity.getRole())); // Exemplo se Role existisse

        return employeeDTO;
    }

    /**
     * Converts EmployeeDTO to EmployeeEntity.
     * Converte EmployeeDTO para EmployeeEntity.
     */
    public EmployeeEntity dtoToEntity(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) return null;

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeDTO.getId()); // <<<--- UUID (Mantém para updates)
        employeeEntity.setUserId(employeeDTO.getUserId()); // <<<--- UUID
        employeeEntity.setFirstName(employeeDTO.getFirstName());
        employeeEntity.setLastName(employeeDTO.getLastName());
        employeeEntity.setSocialInsuranceNumber(employeeDTO.getSocialInsuranceNumber());
        employeeEntity.setBirthDate(employeeDTO.getBirthDate());
        employeeEntity.setHireDate(employeeDTO.getHireDate());
        employeeEntity.setTerminationDate(employeeDTO.getTerminationDate()); // Adicionado
        employeeEntity.setEmploymentType(employeeDTO.getEmploymentType()); // Adicionado

        // Usa o AddressConverter injetado
        employeeEntity.setAddress(addressConverter.dtoToEntity(employeeDTO.getAddress()));

        employeeEntity.setSalary(employeeDTO.getSalary());
        employeeEntity.setHourlyRate(employeeDTO.getHourlyRate()); // Adicionado
        employeeEntity.setBenefitsCostMonthly(employeeDTO.getBenefitsCostMonthly()); // Adicionado
        employeeEntity.setPayFrequency(employeeDTO.getPayFrequency());

        // Relações (Role, WorkHours, Allocations) geralmente não são definidas aqui,
        // mas sim no serviço ao criar/atualizar o funcionário ou ao adicionar um item relacionado.

        return employeeEntity;
    }
}