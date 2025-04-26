package com.bufalari.employee.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String socialInsuranceNumber;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private RoleDTO role;
    private AddressDTO address;
    private Double salary;
    private String payFrequency;
}
