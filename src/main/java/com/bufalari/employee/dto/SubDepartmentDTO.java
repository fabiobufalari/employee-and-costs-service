package com.bufalari.employee.dto;

import lombok.Data;

@Data
public class SubDepartmentDTO {

    private Long id;
    private String name;
    private DepartmentDTO department;
}
