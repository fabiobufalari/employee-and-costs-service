package com.bufalari.employee.convert;


import com.bufalari.employee.dto.DepartmentDTO;
import com.bufalari.employee.entity.DepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter {

    public DepartmentDTO entityToDTO(DepartmentEntity departmentEntity) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setId(departmentEntity.getId());
        departmentDTO.setName(departmentEntity.getName());
        departmentDTO.setCompany(new CompanyConverter().entityToDTO(departmentEntity.getCompany()));
        // Omitted subDepartments for brevity

        return departmentDTO;
    }

    public DepartmentEntity dtoToEntity(DepartmentDTO departmentDTO) {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(departmentDTO.getId());
        departmentEntity.setName(departmentDTO.getName());
        departmentEntity.setCompany(new CompanyConverter().dtoToEntity(departmentDTO.getCompany()));
        // Omitted subDepartments for brevity

        return departmentEntity;
    }
}
