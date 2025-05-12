package com.bufalari.employee.repository;

import com.bufalari.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar Optional
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Repositório para operações com EmployeeEntity (usando UUID).
 * Repository for operations with EmployeeEntity (using UUID).
 */
@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> { // <<<--- Changed to UUID

    // Métodos CRUD básicos (findById, existsById, save, findAll, deleteById) são herdados
    // e agora funcionam com UUID.

    // Exemplo de método customizado (se necessário):
    /**
     * Busca um funcionário pelo seu userId (UUID do serviço de autenticação).
     * Finds an employee by their userId (UUID from the authentication service).
     * @param userId The UUID linking to the auth user. / O UUID que liga ao usuário de autenticação.
     * @return Optional containing the employee if found. / Optional contendo o funcionário se encontrado.
     */
    Optional<EmployeeEntity> findByUserId(UUID userId); // <<<--- Exemplo com UUID

    /**
     * Verifica se existe um funcionário com o userId fornecido.
     * Checks if an employee exists with the given userId.
     * @param userId The UUID linking to the auth user.
     * @return true if exists, false otherwise.
     */
     boolean existsByUserId(UUID userId); // <<<--- Exemplo com UUID

    /**
     * Busca um funcionário pelo número de identificação social (ex: SIN, CPF).
     * Finds an employee by their social insurance number (e.g., SIN, CPF).
     * @param sin The social insurance number.
     * @return Optional containing the employee if found.
     */
     Optional<EmployeeEntity> findBySocialInsuranceNumber(String sin);

}