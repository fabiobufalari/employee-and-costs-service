package com.bufalari.employee.convert;


import com.bufalari.employee.dto.SubDepartmentDTO;
import com.bufalari.employee.entity.SubDepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class SubDepartmentConverter {

    public SubDepartmentDTO entityToDTO(SubDepartmentEntity subDepartmentEntity) {
        SubDepartmentDTO subDepartmentDTO = new SubDepartmentDTO();
        subDepartmentDTO.setId(subDepartmentEntity.getId());
        subDepartmentDTO.setName(subDepartmentEntity.getName());
        subDepartmentDTO.setDepartment(new DepartmentConverter().entityToDTO(subDepartmentEntity.getDepartment()));

        return subDepartmentDTO;
    }

    public SubDepartmentEntity dtoToEntity(SubDepartmentDTO subDepartmentDTO) {
        SubDepartmentEntity subDepartmentEntity = new SubDepartmentEntity();
        subDepartmentEntity.setId(subDepartmentDTO.getId());
        subDepartmentEntity.setName(subDepartmentDTO.getName());
        subDepartmentEntity.setDepartment(new DepartmentConverter().dtoToEntity(subDepartmentDTO.getDepartment()));

        return subDepartmentEntity;
    }
}
