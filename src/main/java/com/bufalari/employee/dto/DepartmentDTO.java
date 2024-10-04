package com.bufalari.employee.dto;

import lombok.Data;
import java.util.List;

@Data
public class DepartmentDTO {
    private Long id;
    private String name;
    private CompanyDTO company;
    private List<SubDepartmentDTO> subDepartments;
}
