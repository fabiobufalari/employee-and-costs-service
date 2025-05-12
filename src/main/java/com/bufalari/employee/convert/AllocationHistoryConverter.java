package com.bufalari.employee.convert;

import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.entity.AllocationHistoryEntity;
// import com.bufalari.employee.entity.EmployeeEntity; // Import não necessário aqui
import org.springframework.stereotype.Component;

import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Converts between AllocationHistoryEntity (with UUID ID) and AllocationHistoryDTO (with UUID ID).
 * Conversor entre AllocationHistoryEntity (com ID UUID) e AllocationHistoryDTO (com ID UUID).
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
        dto.setId(entity.getId()); // <<<--- UUID
        // Garante que o funcionário não seja nulo antes de acessar o ID
        if (entity.getEmployee() != null) {
            dto.setEmployeeId(entity.getEmployee().getId()); // <<<--- UUID
        }
        dto.setProjectId(entity.getProjectId()); // Assume que Project ID continua Long
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
        entity.setId(dto.getId()); // <<<--- UUID (Mantém para atualizações)
        entity.setProjectId(dto.getProjectId()); // Assume que Project ID continua Long
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        // O campo 'employee' deve ser definido no serviço que usa este conversor
        // O serviço buscará o EmployeeEntity usando o dto.getEmployeeId() (que agora é UUID)
        // entity.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElse(null)); // Exemplo no serviço
        return entity;
    }
}