package com.bufalari.employee.convert;


import com.bufalari.employee.dto.EmployeeDTO;
import com.bufalari.employee.entity.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConverter {

    public EmployeeDTO entityToDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employeeEntity.getId());
        employeeDTO.setFirstName(employeeEntity.getFirstName());
        employeeDTO.setLastName(employeeEntity.getLastName());
        employeeDTO.setSocialInsuranceNumber(employeeEntity.getSocialInsuranceNumber());
        employeeDTO.setBirthDate(employeeEntity.getBirthDate());
        employeeDTO.setHireDate(employeeEntity.getHireDate());
        employeeDTO.setCompany(new CompanyConverter().entityToDTO(employeeEntity.getCompany()));
        employeeDTO.setRole(new RoleConverter().entityToDTO(employeeEntity.getRole()));
        employeeDTO.setAddress(new AddressConverter().entityToDTO(employeeEntity.getAddress()));
        employeeDTO.setSalary(employeeEntity.getSalary());
        employeeDTO.setPayFrequency(employeeEntity.getPayFrequency());
        // Omitted contacts for brevity

        return employeeDTO;
    }

    public EmployeeEntity dtoToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employeeDTO.getId());
        employeeEntity.setFirstName(employeeDTO.getFirstName());
        employeeEntity.setLastName(employeeDTO.getLastName());
        employeeEntity.setSocialInsuranceNumber(employeeDTO.getSocialInsuranceNumber());
        employeeEntity.setBirthDate(employeeDTO.getBirthDate());
        employeeEntity.setHireDate(employeeDTO.getHireDate());
        employeeEntity.setCompany(new CompanyConverter().dtoToEntity(employeeDTO.getCompany()));
        employeeEntity.setRole(new RoleConverter().dtoToEntity(employeeDTO.getRole()));
        employeeEntity.setAddress(new AddressConverter().dtoToEntity(employeeDTO.getAddress()));
        employeeEntity.setSalary(employeeDTO.getSalary());
        employeeEntity.setPayFrequency(employeeDTO.getPayFrequency());
        // Omitted contacts for brevity

        return employeeEntity;
    }
}
