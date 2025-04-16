package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entidade que representa as horas trabalhadas por um funcionário em uma obra.
 * Entity representing the hours worked by an employee on a construction project.
 */
@Entity
@Data
@Table(name = "work_hours")
public class WorkHoursEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "construction_id", nullable = false)
    private Long constructionId;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "hours_worked", nullable = false)
    private Double hoursWorked;
}