package com.bufalari.employee.repository;

import com.bufalari.employee.entity.AllocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Repositório para operações com AllocationHistoryEntity (usando UUID).
 * Repository for operations with AllocationHistoryEntity (using UUID).
 */
@Repository
public interface AllocationHistoryRepository extends JpaRepository<AllocationHistoryEntity, UUID> { // <<<--- Changed to UUID

    /**
     * Busca o histórico de alocação por ID do funcionário (UUID).
     * Finds allocation history by employee ID (UUID).
     * Spring Data JPA infere a query a partir do nome do método e do campo 'employee.id'.
     */
    List<AllocationHistoryEntity> findByEmployeeId(UUID employeeId); // <<<--- Changed to UUID
}