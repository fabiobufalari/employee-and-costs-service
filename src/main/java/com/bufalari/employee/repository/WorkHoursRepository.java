package com.bufalari.employee.repository;

import com.bufalari.employee.entity.WorkHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate; // Importar LocalDate
import java.util.List; // Importar List
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Repositório para operações com WorkHoursEntity (usando UUID).
 * Repository for operations with WorkHoursEntity (using UUID).
 */
@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHoursEntity, UUID> { // <<<--- Changed to UUID

    // Métodos CRUD básicos herdados.

    // Exemplos de métodos de consulta customizados:

    /**
     * Busca todas as horas trabalhadas para um funcionário específico.
     * Finds all work hours for a specific employee.
     * @param employeeId The UUID of the employee.
     * @return List of work hours entities.
     */
    List<WorkHoursEntity> findByEmployeeIdOrderByWorkDateDesc(UUID employeeId); // <<<--- Changed to UUID

    /**
     * Busca todas as horas trabalhadas para um funcionário dentro de um intervalo de datas.
     * Finds all work hours for a specific employee within a date range.
     * @param employeeId The UUID of the employee.
     * @param startDate The start date (inclusive).
     * @param endDate The end date (inclusive).
     * @return List of work hours entities.
     */
    List<WorkHoursEntity> findByEmployeeIdAndWorkDateBetweenOrderByWorkDateAsc(UUID employeeId, LocalDate startDate, LocalDate endDate); // <<<--- Changed to UUID

    /**
     * Busca todas as horas alocadas a um projeto específico.
     * Finds all work hours allocated to a specific project.
     * @param projectId The ID of the project (assuming Long).
     * @return List of work hours entities.
     */
    List<WorkHoursEntity> findByProjectId(Long projectId);

    /**
     * Busca todas as horas alocadas a um centro de custo específico.
     * Finds all work hours allocated to a specific cost center.
     * @param costCenterId The ID of the cost center (assuming Long).
     * @return List of work hours entities.
     */
    List<WorkHoursEntity> findByCostCenterId(Long costCenterId);
}