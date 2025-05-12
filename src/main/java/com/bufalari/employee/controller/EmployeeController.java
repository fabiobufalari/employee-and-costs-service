package com.bufalari.employee.controller;

import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter; // Importar Parameter
import io.swagger.v3.oas.annotations.media.Content; // Importar Content
import io.swagger.v3.oas.annotations.media.Schema; // Importar Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag; // Importar Tag
import jakarta.validation.Valid; // Importar @Valid
import lombok.RequiredArgsConstructor; // Usar Lombok
import org.slf4j.Logger; // Importar Logger
import org.slf4j.LoggerFactory; // Importar LoggerFactory
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.MediaType; // Importar MediaType
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Para Location header

import java.net.URI; // Importar URI
import java.util.List;
import java.util.UUID; // <<<--- IMPORT UUID

/**
 * Controlador para gerenciamento de funcionários (usando UUID), incluindo horas trabalhadas e alocação.
 * Controller for managing employees (using UUID), including work hours and allocation.
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor // Injeção via construtor
@Tag(name = "Employee Management", description = "Endpoints for managing employees, work hours, and allocations")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    /**
     * Cria um novo funcionário.
     */
    @Operation(summary = "Create a new employee", description = "Creates a new employee record in the system.")
    @ApiResponses(value = { // Usar value = { ... }
            @ApiResponse(responseCode = "201", description = "Employee created successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER')") // Exemplo: ADMIN ou Gerente de RH
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Request received to create employee: {} {}", employeeDTO.getFirstName(), employeeDTO.getLastName());
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        // Cria URI para o novo recurso
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdEmployee.getId()) // Usa o UUID retornado
                .toUri();
        log.info("Employee created successfully with ID {} at {}", createdEmployee.getId(), location);
        return ResponseEntity.created(location).body(createdEmployee); // Retorna 201 Created
    }

    /**
     * Busca um funcionário por UUID.
     */
    @Operation(summary = "Get employee by ID", description = "Retrieves an employee by their UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()") // Permitir qualquer usuário autenticado ver (ajustar se necessário)
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @Parameter(description = "UUID of the employee") @PathVariable UUID id) { // <<<--- UUID
        log.debug("Request received to get employee by ID: {}", id);
        return employeeService.getEmployeeById(id) // Serviço retorna Optional<DTO>
                .map(dto -> {
                    log.info("Employee found with ID: {}", id);
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Lista todos os funcionários.
     */
    @Operation(summary = "List all employees", description = "Retrieves a list of all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees returned", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'HR_MANAGER')") // Exemplo de roles que podem listar
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.debug("Request received to list all employees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        log.info("Returning {} employees", employees.size());
        return ResponseEntity.ok(employees);
    }

    /**
     * Atualiza um funcionário existente por UUID.
     */
    @Operation(summary = "Update an employee", description = "Updates an existing employee by their UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR_MANAGER')") // Exemplo
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @Parameter(description = "UUID of the employee to update") @PathVariable UUID id, // <<<--- UUID
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Request received to update employee ID: {}", id);
        // Opcional: Validar se ID do path e body coincidem
        if (employeeDTO.getId() != null && !employeeDTO.getId().equals(id)) {
            log.warn("Path ID {} does not match body ID {}. Using path ID for update.", id, employeeDTO.getId());
        }
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO); // Serviço trata not found
        log.info("Employee updated successfully for ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Exclui um funcionário por UUID.
     */
    @Operation(summary = "Delete an employee", description = "Deletes an employee by their UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "409", description = "Conflict (e.g., cannot delete employee with active allocations)"), // Exemplo 409
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin deleta
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "UUID of the employee to delete") @PathVariable UUID id) { // <<<--- UUID
        log.info("Request received to delete employee ID: {}", id);
        employeeService.deleteEmployee(id); // Serviço trata not found e conflitos
        log.info("Employee deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    // --- Horas Trabalhadas ---

    @Operation(summary = "Register work hours", description = "Registers work hours for an employee (identified by UUID) on a project/cost center.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work hours registered successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = WorkHoursDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/{employeeId}/work-hours", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'EMPLOYEE')") // Permitir que o próprio funcionário registre? Ajustar permissão.
    public ResponseEntity<WorkHoursDTO> registerWorkHours(
            @Parameter(description = "UUID of the employee") @PathVariable UUID employeeId, // <<<--- UUID
            @Valid @RequestBody WorkHoursDTO workHoursDTO) {
        log.info("Request received to register work hours for employee ID: {}", employeeId);
        // Garante que o employeeId no DTO (se existir) seja o mesmo do path
        if (workHoursDTO.getEmployeeId() != null && !workHoursDTO.getEmployeeId().equals(employeeId)) {
            log.warn("Path employeeId {} does not match body employeeId {}. Using path ID.", employeeId, workHoursDTO.getEmployeeId());
            // Poderia lançar Bad Request aqui
        }
        workHoursDTO.setEmployeeId(employeeId); // Garante que o ID do path seja usado

        WorkHoursDTO savedWorkHours = employeeService.registerWorkHours(employeeId, workHoursDTO);
        // Cria URI para o novo recurso (assumindo um endpoint para buscar work hours por ID)
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/work-hours/{id}") // Exemplo de path
                .buildAndExpand(savedWorkHours.getId())
                .toUri();
        log.info("Work hours registered successfully with ID {} for employee {}", savedWorkHours.getId(), employeeId);
        return ResponseEntity.created(location).body(savedWorkHours);
    }

    // --- Alocação ---

    @Operation(summary = "Allocate employee to project", description = "Creates an allocation record for an employee (identified by UUID) to a project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee allocated successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AllocationHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., overlapping dates)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/{employeeId}/allocations", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // Apenas Admin/Manager alocam
    public ResponseEntity<AllocationHistoryDTO> allocateEmployee(
            @Parameter(description = "UUID of the employee to allocate") @PathVariable UUID employeeId, // <<<--- UUID
            @Valid @RequestBody AllocationHistoryDTO allocationDTO) {
        log.info("Request received to allocate employee ID {} to project ID {}", employeeId, allocationDTO.getProjectId());
        // Garante que o employeeId no DTO (se existir) seja o mesmo do path
        if (allocationDTO.getEmployeeId() != null && !allocationDTO.getEmployeeId().equals(employeeId)) {
            log.warn("Path employeeId {} does not match body employeeId {}. Using path ID.", employeeId, allocationDTO.getEmployeeId());
        }
        allocationDTO.setEmployeeId(employeeId);

        AllocationHistoryDTO savedAllocation = employeeService.allocateEmployee(employeeId, allocationDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/allocations/{id}") // Exemplo de path
                .buildAndExpand(savedAllocation.getId())
                .toUri();
        log.info("Allocation created successfully with ID {} for employee {}", savedAllocation.getId(), employeeId);
        return ResponseEntity.created(location).body(savedAllocation);
    }

    @Operation(summary = "Get allocation history", description = "Retrieves the allocation history for a specific employee (by UUID).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Allocation history retrieved successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{employeeId}/allocations/history", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')") // Apenas Admin/Manager veem histórico
    public ResponseEntity<List<AllocationHistoryDTO>> getAllocationHistory(
            @Parameter(description = "UUID of the employee") @PathVariable UUID employeeId) { // <<<--- UUID
        log.debug("Request received for allocation history of employee ID: {}", employeeId);
        List<AllocationHistoryDTO> history = employeeService.getAllocationHistory(employeeId);
        log.info("Returning {} allocation history records for employee {}", history.size(), employeeId);
        return ResponseEntity.ok(history);
    }
}