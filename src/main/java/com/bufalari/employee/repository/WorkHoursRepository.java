package com.bufalari.employee.repository;

import com.bufalari.employee.entity.WorkHoursEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações com WorkHoursEntity.
 * Repository for operations with WorkHoursEntity.
 */
@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHoursEntity, Long> {
}