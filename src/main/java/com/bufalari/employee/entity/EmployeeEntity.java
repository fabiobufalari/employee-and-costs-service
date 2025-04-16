package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Entidade que representa um funcionário no sistema.
 * Entity representing an employee in the system.
 */
@Entity
@Data
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String socialInsuranceNumber;
    private LocalDate birthDate;
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @Embedded
    private AddressEntity address;

    @OneToMany(mappedBy = "employee")
    private List<ContactEntity> contacts;

    private Double salary;
    private String payFrequency;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkHoursEntity> workHours;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AllocationHistoryEntity> allocationHistory;
}