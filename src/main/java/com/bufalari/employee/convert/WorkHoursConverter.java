package com.bufalari.employee.convert;

import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.entity.WorkHoursEntity;
import org.springframework.stereotype.Component;

import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Conversor entre WorkHoursEntity (com ID UUID) e WorkHoursDTO (com ID UUID).
 * Converter between WorkHoursEntity (with UUID ID) and WorkHoursDTO (with UUID ID).
 */
@Component
public class WorkHoursConverter {

    /**
     * Converte uma entidade WorkHoursEntity para WorkHoursDTO.
     * Converts a WorkHoursEntity to WorkHoursDTO.
     */
    public WorkHoursDTO entityToDTO(WorkHoursEntity entity) {
        if (entity == null) return null;

        WorkHoursDTO dto = new WorkHoursDTO();
        dto.setId(entity.getId()); // <<<--- UUID
        // Garante que o funcionário não seja nulo antes de pegar o ID
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId()); // <<<--- UUID
        }
        dto.setProjectId(entity.getProjectId()); // Assume Long
        dto.setCostCenterId(entity.getCostCenterId()); // Assume Long
        dto.setWorkDate(entity.getWorkDate());
        dto.setHoursWorked(entity.getHoursWorked()); // Assume BigDecimal
        dto.setCalculatedCost(entity.getCalculatedCost()); // Assume BigDecimal
        dto.setDescription(entity.getDescription());

        return dto;
    }

    /**
     * Converte um WorkHoursDTO para WorkHoursEntity.
     * A relação com EmployeeEntity deve ser definida no serviço.
     * Converts a WorkHoursDTO to WorkHoursEntity.
     * The relationship with EmployeeEntity must be set in the service.
     */
    public WorkHoursEntity dtoToEntity(WorkHoursDTO dto) {
        if (dto == null) return null;

        WorkHoursEntity entity = new WorkHoursEntity();
        entity.setId(dto.getId()); // <<<--- UUID (Mantém para updates)
        entity.setProjectId(dto.getProjectId()); // Assume Long
        entity.setCostCenterId(dto.getCostCenterId()); // Assume Long
        entity.setWorkDate(dto.getWorkDate());
        entity.setHoursWorked(dto.getHoursWorked()); // Assume BigDecimal
        entity.setCalculatedCost(dto.getCalculatedCost()); // Assume BigDecimal
        entity.setDescription(dto.getDescription());
        // O campo 'employee' deve ser definido no serviço usando o employeeId (UUID) do DTO
        // Ex: entity.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElse(null));

        return entity;
    }
}