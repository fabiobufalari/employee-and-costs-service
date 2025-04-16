package com.bufalari.employee.repository;

import com.bufalari.employee.entity.AllocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações com AllocationHistoryEntity.
 * Repository for operations with AllocationHistoryEntity.
 */
@Repository
public interface AllocationHistoryRepository extends JpaRepository<AllocationHistoryEntity, Long> {
    /**
     * Busca o histórico de alocação por ID do funcionário.
     * Finds allocation history by employee ID.
     */
    List<AllocationHistoryEntity> findByEmployeeId(Long employeeId);
}