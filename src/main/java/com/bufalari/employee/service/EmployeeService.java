package com.bufalari.employee.service;

import com.bufalari.employee.convert.AllocationHistoryConverter;
import com.bufalari.employee.convert.EmployeeConverter;
import com.bufalari.employee.convert.WorkHoursConverter;
import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.entity.AllocationHistoryEntity;
import com.bufalari.employee.entity.EmployeeEntity;
import com.bufalari.employee.entity.WorkHoursEntity;
import com.bufalari.employee.exception.ResourceNotFoundException; // Importar exceção
import com.bufalari.employee.repository.AllocationHistoryRepository;
import com.bufalari.employee.repository.EmployeeRepository;
import com.bufalari.employee.repository.WorkHoursRepository;
import lombok.RequiredArgsConstructor; // Usar Lombok para injeção
import org.slf4j.Logger; // Importar Logger
import org.slf4j.LoggerFactory; // Importar LoggerFactory
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar Transactional

import java.util.List;
import java.util.Optional;
import java.util.UUID; // <<<--- IMPORT UUID
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de funcionários (usando UUID), incluindo horas e alocação.
 * Service for managing employees (using UUID), including work hours and allocation history.
 */
@Service
@RequiredArgsConstructor // Injeção via construtor
@Transactional // Transacionalidade padrão
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private final WorkHoursRepository workHoursRepository;
    private final WorkHoursConverter workHoursConverter;
    private final AllocationHistoryRepository allocationHistoryRepository;
    private final AllocationHistoryConverter allocationHistoryConverter;

    /**
     * Cria um novo funcionário.
     * Creates a new employee.
     * @param employeeDTO DTO com dados do funcionário.
     * @return DTO do funcionário criado com UUID.
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Attempting to create employee: {} {}", employeeDTO.getFirstName(), employeeDTO.getLastName());
        if (employeeDTO.getId() != null) {
             log.warn("ID provided for employee creation will be ignored.");
             employeeDTO.setId(null); // Garante ID nulo na criação
        }
        // Adicionar validações extras se necessário (ex: verificar se userId já existe)
        // if (employeeDTO.getUserId() != null && employeeRepository.existsByUserId(employeeDTO.getUserId())) {
        //     throw new IllegalArgumentException("User ID already linked to another employee.");
        // }

        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return employeeConverter.entityToDTO(savedEmployee);
    }

    /**
     * Busca um funcionário por UUID.
     * Finds an employee by UUID.
     * @param id O UUID do funcionário.
     * @return Optional contendo o EmployeeDTO se encontrado.
     */
    @Transactional(readOnly = true) // Otimização para leitura
    public Optional<EmployeeDTO> getEmployeeById(UUID id) { // <<<--- UUID
        log.debug("Fetching employee by ID: {}", id);
        return employeeRepository.findById(id).map(employeeConverter::entityToDTO); // <<<--- findById com UUID
    }

    /**
     * Lista todos os funcionários.
     * Lists all employees.
     * @return Lista de EmployeeDTOs.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        log.debug("Fetching all employees.");
        List<EmployeeEntity> employees = employeeRepository.findAll();
        log.info("Found {} employees.", employees.size());
        return employees.stream()
                .map(employeeConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um funcionário existente por UUID.
     * Updates an existing employee by UUID.
     * @param id O UUID do funcionário a atualizar.
     * @param employeeDTO DTO com os dados atualizados.
     * @return O EmployeeDTO atualizado.
     * @throws ResourceNotFoundException se o funcionário não for encontrado.
     */
    public EmployeeDTO updateEmployee(UUID id, EmployeeDTO employeeDTO) { // <<<--- UUID
        log.info("Attempting to update employee with ID: {}", id);
        // Verifica se o funcionário existe
        if (!employeeRepository.existsById(id)) { // <<<--- existsById com UUID
            log.warn("Update failed. Employee with ID {} not found.", id);
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }
        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        employeeEntity.setId(id); // Garante que o ID correto está sendo usado para update

        // Adicionar validações extras se necessário (ex: verificar se userId está mudando e já existe)
        // EmployeeEntity existing = employeeRepository.findById(id).get(); // Carregar existente para comparar
        // if (!Objects.equals(existing.getUserId(), employeeEntity.getUserId()) && employeeEntity.getUserId() != null) {
        //     if (employeeRepository.existsByUserId(employeeEntity.getUserId())) {
        //         throw new IllegalArgumentException("User ID " + employeeEntity.getUserId() + " is already linked to another employee.");
        //     }
        // }


        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        log.info("Employee with ID {} updated successfully.", id);
        return employeeConverter.entityToDTO(updatedEmployee);
    }

    /**
     * Exclui um funcionário por UUID.
     * Deletes an employee by UUID.
     * @param id O UUID do funcionário a deletar.
     * @throws ResourceNotFoundException se o funcionário não for encontrado.
     */
    public void deleteEmployee(UUID id) { // <<<--- UUID
        log.info("Attempting to delete employee with ID: {}", id);
        if (!employeeRepository.existsById(id)) { // <<<--- existsById com UUID
             log.warn("Delete failed. Employee with ID {} not found.", id);
            throw new ResourceNotFoundException("Employee not found with ID: " + id);
        }
        // Adicionar verificações de dependência se necessário (ex: alocações ativas?)
        employeeRepository.deleteById(id); // <<<--- deleteById com UUID
        log.info("Employee with ID {} deleted successfully.", id);
    }

    // --- Horas Trabalhadas ---

    /**
     * Registra horas trabalhadas para um funcionário (por UUID).
     * Registers work hours for an employee (by UUID).
     * @param employeeId UUID do funcionário.
     * @param workHoursDTO DTO das horas trabalhadas.
     * @return DTO das horas salvas com UUID.
     * @throws ResourceNotFoundException se o funcionário não for encontrado.
     */
    public WorkHoursDTO registerWorkHours(UUID employeeId, WorkHoursDTO workHoursDTO) { // <<<--- UUID
        log.info("Registering work hours for employee ID: {}", employeeId);
        EmployeeEntity employee = employeeRepository.findById(employeeId) // <<<--- findById com UUID
                .orElseThrow(() -> {
                     log.warn("Failed to register work hours: Employee not found with ID {}", employeeId);
                     return new ResourceNotFoundException("Employee not found with ID: " + employeeId);
                });

        WorkHoursEntity workHoursEntity = workHoursConverter.dtoToEntity(workHoursDTO);
        workHoursEntity.setEmployee(employee); // Define a relação

        // Validação de alocação (projeto/centro custo) e cálculo de custo ocorrem no @PrePersist/Update da entidade
        WorkHoursEntity savedWorkHours = workHoursRepository.save(workHoursEntity);
        log.info("Work hours record created with ID {} for employee {}", savedWorkHours.getId(), employeeId);
        return workHoursConverter.entityToDTO(savedWorkHours);
    }

    // --- Alocação ---

    /**
     * Aloca um funcionário (por UUID) a um projeto.
     * Allocates an employee (by UUID) to a project.
     * @param employeeId UUID do funcionário.
     * @param allocationDTO DTO da alocação.
     * @return DTO da alocação salva com UUID.
     * @throws ResourceNotFoundException se o funcionário não for encontrado.
     */
    public AllocationHistoryDTO allocateEmployee(UUID employeeId, AllocationHistoryDTO allocationDTO) { // <<<--- UUID
        log.info("Allocating employee ID {} to project ID {}", employeeId, allocationDTO.getProjectId());
        EmployeeEntity employee = employeeRepository.findById(employeeId) // <<<--- findById com UUID
                .orElseThrow(() -> {
                     log.warn("Failed to allocate employee: Employee not found with ID {}", employeeId);
                     return new ResourceNotFoundException("Employee not found with ID: " + employeeId);
                });

        AllocationHistoryEntity allocationEntity = allocationHistoryConverter.dtoToEntity(allocationDTO);
        allocationEntity.setEmployee(employee); // Define a relação

        AllocationHistoryEntity savedAllocation = allocationHistoryRepository.save(allocationEntity);
        log.info("Allocation record created with ID {} for employee {}", savedAllocation.getId(), employeeId);
        return allocationHistoryConverter.entityToDTO(savedAllocation);
    }

    /**
     * Obtém o histórico de alocação de um funcionário por UUID.
     * Retrieves the allocation history of an employee by UUID.
     * @param employeeId O UUID do funcionário.
     * @return Lista de AllocationHistoryDTOs.
     */
    @Transactional(readOnly = true)
    public List<AllocationHistoryDTO> getAllocationHistory(UUID employeeId) { // <<<--- UUID
        log.debug("Fetching allocation history for employee ID: {}", employeeId);
        // Garante que o funcionário existe antes de buscar o histórico
        if (!employeeRepository.existsById(employeeId)) {
            log.warn("Cannot get allocation history: Employee not found with ID {}", employeeId);
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        List<AllocationHistoryEntity> history = allocationHistoryRepository.findByEmployeeId(employeeId); // <<<--- findByEmployeeId com UUID
        log.info("Found {} allocation records for employee ID {}", history.size(), employeeId);
        return history.stream()
                .map(allocationHistoryConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}