package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Entidade que representa o histórico de alocação de um funcionário em obras.
 * Entity representing the allocation history of an employee to construction projects.
 */
@Entity
@Data
@Table(name = "allocation_history")
public class AllocationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "construction_id", nullable = false)
    private Long constructionId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}