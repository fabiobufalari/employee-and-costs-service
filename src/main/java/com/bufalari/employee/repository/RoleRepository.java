package com.bufalari.employee.repository;

import com.bufalari.employee.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações com RoleEntity.
 * Repository for operations with RoleEntity.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    /**
     * Busca um papel pelo nome.
     * Finds a role by name.
     */
    Optional<RoleEntity> findByName(String name);
}