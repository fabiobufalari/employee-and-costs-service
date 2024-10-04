package com.bufalari.employee.service;


import com.bufalari.employee.convert.EmployeeConverter;
import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.entity.EmployeeEntity;
import com.bufalari.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeConverter employeeConverter) {
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        EmployeeEntity savedEmployee = employeeRepository.save(employeeEntity);
        return employeeConverter.entityToDTO(savedEmployee);
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employeeConverter::entityToDTO);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeConverter::entityToDTO).collect(Collectors.toList());
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found");
        }
        EmployeeEntity employeeEntity = employeeConverter.dtoToEntity(employeeDTO);
        employeeEntity.setId(id);
        EmployeeEntity updatedEmployee = employeeRepository.save(employeeEntity);
        return employeeConverter.entityToDTO(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
