package com.bufalari.employee.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "sub_departments")
public class SubDepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;
}
