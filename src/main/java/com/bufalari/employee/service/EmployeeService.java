package com.bufalari.employee.service;

import com.bufalari.employee.convert.EmployeeConverter;
import com.bufalari.employee.convert.WorkHoursConverter;
import com.bufalari.employee.convert.AllocationHistoryConverter;
import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.dto.WorkHoursDTO;
import com.bufalari.employee.dto.AllocationHistoryDTO;
import com.bufalari.employee.entity.EmployeeEntity;
import com.bufalari.employee.entity.WorkHoursEntity;
import com.bufalari.employee.entity.AllocationHistoryEntity;
import com.bufalari.employee.repository.EmployeeRepository;
import com.bufalari.employee.repository.WorkHoursRepository;
import com.bufalari.employee.repository.AllocationHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de funcionários, incluindo horas trabalhadas e histórico de alocação.
 * Service for managing employees, including work hours and allocation history.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private final WorkHoursRepository workHoursRepository;
    private final WorkHoursConverter workHoursConverter;
    private final AllocationHistoryRepository allocationHistoryRepository;
    private final AllocationHistoryConverter allocationHistoryConverter;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeConverter employeeConverter,
            WorkHoursRepository workHoursRepository,
            WorkHoursConverter workHoursConverter,
            AllocationHistoryRepository allocationHistoryRepository,
            AllocationHistoryConverter allocationHistoryConverter) {
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
        this.workHoursRepository = workHoursRepository;
        this.workHoursConverter = workHoursConverter;
        this.allocationHistoryRepository = allocationHistoryRepository;
        this.allocationHistoryConverter = allocationHistoryConverter;
    }

    /**
     * Cria um novo funcionário.
     * Creates a new employee.
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return employeeConverter.entityToDTO(savedEmployee);
    }

    /**
     * Busca um funcionário por ID.
     * Finds an employee by ID.
     */
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employeeConverter::entityToDTO);
    }

    /**
     * Lista todos os funcionários.
     * Lists all employees.
     */
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um funcionário existente.
     * Updates an existing employee.
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found");
        }
        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        employeeEntity.setId(id);
        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        return employeeConverter.entityToDTO(updatedEmployee);
    }

    /**
     * Exclui um funcionário por ID.
     * Deletes an employee by ID.
     */
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Registra horas trabalhadas para um funcionário.
     * Registers work hours for an employee.
     */
    public WorkHoursDTO registerWorkHours(Long employeeId, WorkHoursDTO workHoursDTO) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        WorkHoursEntity workHoursEntity = workHoursConverter.dtoToEntity(workHoursDTO);
        workHoursEntity.setEmployee(employee);
        WorkHoursEntity savedWorkHours = workHoursRepository.save(workHoursEntity);
        return workHoursConverter.entityToDTO(savedWorkHours);
    }

    /**
     * Aloca um funcionário a uma obra.
     * Allocates an employee to a construction project.
     */
    public AllocationHistoryDTO allocateEmployee(Long employeeId, AllocationHistoryDTO allocationDTO) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        AllocationHistoryEntity allocationEntity = allocationHistoryConverter.dtoToEntity(allocationDTO);
        allocationEntity.setEmployee(employee);
        AllocationHistoryEntity savedAllocation = allocationHistoryRepository.save(allocationEntity);
        return allocationHistoryConverter.entityToDTO(savedAllocation);
    }

    /**
     * Obtém o histórico de alocação de um funcionário.
     * Retrieves the allocation history of an employee.
     */
    public List<AllocationHistoryDTO> getAllocationHistory(Long employeeId) {
        return allocationHistoryRepository.findByEmployeeId(employeeId).stream()
                .map(allocationHistoryConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}