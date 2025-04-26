// Path: employee-and-costs-service/src/main/java/com/bufalari/employee/convert/AllocationHistoryConverter.java
package com.bufalari.employee.convert;

import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.entity.AllocationHistoryEntity;
import com.bufalari.employee.entity.EmployeeEntity; // Import necessary if setting employee in dtoToEntity
import org.springframework.stereotype.Component;

/**
 * Converts between AllocationHistoryEntity and AllocationHistoryDTO.
 * Conversor entre AllocationHistoryEntity e AllocationHistoryDTO.
 */
@Component
public class AllocationHistoryConverter {

    /**
     * Converts an AllocationHistoryEntity entity to AllocationHistoryDTO.
     * Converte uma entidade AllocationHistoryEntity para AllocationHistoryDTO.
     * @param entity The entity to convert. / A entidade a ser convertida.
     * @return The corresponding DTO. / O DTO correspondente.
     */
    public AllocationHistoryDTO entityToDTO(AllocationHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        AllocationHistoryDTO dto = new AllocationHistoryDTO();
        dto.setId(entity.getId());
        // Ensure employee is not null before accessing ID
        // Garante que o funcionário não seja nulo antes de acessar o ID
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId());
        }
        // Update field name / Atualiza nome do campo
        dto.setProjectId(entity.getProjectId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    /**
     * Converts an AllocationHistoryDTO to AllocationHistoryEntity.
     * Note: The EmployeeEntity relationship needs to be set in the service layer.
     * Converte um AllocationHistoryDTO para AllocationHistoryEntity.
     * Nota: O relacionamento EmployeeEntity precisa ser definido na camada de serviço.
     * @param dto The DTO to convert. / O DTO a ser convertido.
     * @return The corresponding entity. / A entidade correspondente.
     */
    public AllocationHistoryEntity dtoToEntity(AllocationHistoryDTO dto) {
        if (dto == null) {
            return null;
        }
        AllocationHistoryEntity entity = new AllocationHistoryEntity();
        entity.setId(dto.getId()); // Keep ID for updates / Mantém ID para atualizações
        // Update field name / Atualiza nome do campo
        entity.setProjectId(dto.getProjectId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        // The 'employee' field must be set in the service that uses this converter
        // O campo 'employee' deve ser definido no serviço que usa este conversor
        // entity.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElse(null)); // Example
        return entity;
    }
}