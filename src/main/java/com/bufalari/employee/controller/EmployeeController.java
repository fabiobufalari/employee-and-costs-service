package com.bufalari.employee.controller;

import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de funcionários, incluindo horas trabalhadas e alocação.
 * Controller for managing employees, including work hours and allocation.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Cria um novo funcionário.
     * Creates a new employee.
     */
    @Operation(summary = "Create a new employee", description = "Creates a new employee in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee created successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    /**
     * Busca um funcionário por ID.
     * Finds an employee by ID.
     */
    @Operation(summary = "Get employee by ID", description = "Retrieves an employee by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista todos os funcionários.
     * Lists all employees.
     */
    @Operation(summary = "List all employees", description = "Retrieves a list of all employees.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of employees"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Atualiza um funcionário existente.
     * Updates an existing employee.
     */
    @Operation(summary = "Update an employee", description = "Updates an existing employee by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Exclui um funcionário por ID.
     * Deletes an employee by ID.
     */
    @Operation(summary = "Delete an employee", description = "Deletes an employee by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Registra horas trabalhadas para um funcionário.
     * Registers work hours for an employee.
     */
    @Operation(summary = "Register work hours", description = "Registers work hours for an employee on a construction project.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Work hours registered successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @PostMapping("/{id}/work-hours")
    @PreAuthorize("hasRole('MANAGER') or hasRole('WORKER')")
    public ResponseEntity<WorkHoursDTO> registerWorkHours(
            @PathVariable Long id,
            @RequestBody WorkHoursDTO workHoursDTO) {
        WorkHoursDTO savedWorkHours = employeeService.registerWorkHours(id, workHoursDTO);
        return ResponseEntity.ok(savedWorkHours);
    }

    /**
     * Aloca um funcionário a uma obra.
     * Allocates an employee to a construction project.
     */
    @Operation(summary = "Allocate employee to project", description = "Allocates an employee to a construction project.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee allocated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @PostMapping("/{id}/allocations")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<AllocationHistoryDTO> allocateEmployee(
            @PathVariable Long id,
            @RequestBody AllocationHistoryDTO allocationDTO) {
        AllocationHistoryDTO savedAllocation = employeeService.allocateEmployee(id, allocationDTO);
        return ResponseEntity.ok(savedAllocation);
    }

    /**
     * Obtém o histórico de alocação de um funcionário.
     * Retrieves the allocation history of an employee.
     */
    @Operation(summary = "Get allocation history", description = "Retrieves the allocation history of an employee.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Allocation history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
    })
    @GetMapping("/{id}/allocations/history")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<AllocationHistoryDTO>> getAllocationHistory(@PathVariable Long id) {
        List<AllocationHistoryDTO> history = employeeService.getAllocationHistory(id);
        return ResponseEntity.ok(history);
    }
}