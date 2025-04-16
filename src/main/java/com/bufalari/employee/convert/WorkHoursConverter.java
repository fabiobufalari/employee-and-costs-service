package com.bufalari.employee.convert;

import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.entity.WorkHoursEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre WorkHoursEntity e WorkHoursDTO.
 * Converter between WorkHoursEntity and WorkHoursDTO.
 */
@Component
public class WorkHoursConverter {

    /**
     * Converte uma entidade WorkHoursEntity para WorkHoursDTO.
     * Converts a WorkHoursEntity to WorkHoursDTO.
     */
    public WorkHoursDTO entityToDTO(WorkHoursEntity entity) {
        WorkHoursDTO dto = new WorkHoursDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setConstructionId(entity.getConstructionId());
        dto.setWorkDate(entity.getWorkDate());
        dto.setHoursWorked(entity.getHoursWorked());
        return dto;
    }

    /**
     * Converte um WorkHoursDTO para WorkHoursEntity.
     * Converts a WorkHoursDTO to WorkHoursEntity.
     */
    public WorkHoursEntity dtoToEntity(WorkHoursDTO dto) {
        WorkHoursEntity entity = new WorkHoursEntity();
        entity.setId(dto.getId());
        entity.setConstructionId(dto.getConstructionId());
        entity.setWorkDate(dto.getWorkDate());
        entity.setHoursWorked(dto.getHoursWorked());
        return entity;
    }
}