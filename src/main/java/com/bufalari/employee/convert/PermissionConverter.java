package com.bufalari.employee.convert;


import com.bufalari.employee.dto.PermissionDTO;
import com.bufalari.employee.entity.PermissionEntity;
import org.springframework.stereotype.Component;

@Component
public class PermissionConverter {

    public PermissionDTO entityToDTO(PermissionEntity permissionEntity) {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setId(permissionEntity.getId());
        permissionDTO.setName(permissionEntity.getName());
        permissionDTO.setDescription(permissionEntity.getDescription());

        return permissionDTO;
    }

    public PermissionEntity dtoToEntity(PermissionDTO permissionDTO) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(permissionDTO.getId());
        permissionEntity.setName(permissionDTO.getName());
        permissionEntity.setDescription(permissionDTO.getDescription());

        return permissionEntity;
    }
}
