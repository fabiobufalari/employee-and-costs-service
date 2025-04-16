package com.bufalari.employee.convert;

import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.entity.AllocationHistoryEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre AllocationHistoryEntity e AllocationHistoryDTO.
 * Converter between AllocationHistoryEntity and AllocationHistoryDTO.
 */
@Component
public class AllocationHistoryConverter {

    /**
     * Converte uma entidade AllocationHistoryEntity para AllocationHistoryDTO.
     * Converts an AllocationHistoryEntity to AllocationHistoryDTO.
     */
    public AllocationHistoryDTO entityToDTO(AllocationHistoryEntity entity) {
        AllocationHistoryDTO dto = new AllocationHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setConstructionId(entity.getConstructionId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    /**
     * Converte um AllocationHistoryDTO para AllocationHistoryEntity.
     * Converts an AllocationHistoryDTO to AllocationHistoryEntity.
     */
    public AllocationHistoryEntity dtoToEntity(AllocationHistoryDTO dto) {
        AllocationHistoryEntity entity = new AllocationHistoryEntity();
        entity.setId(dto.getId());
        entity.setConstructionId(dto.getConstructionId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        return entity;
    }
}