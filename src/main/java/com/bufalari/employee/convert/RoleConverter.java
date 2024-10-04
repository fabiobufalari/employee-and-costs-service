package com.bufalari.employee.convert;


import com.bufalari.employee.dto.RoleDTO;
import com.bufalari.employee.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleConverter {

    public RoleDTO entityToDTO(RoleEntity roleEntity) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(roleEntity.getId());
        roleDTO.setName(roleEntity.getName());
        roleDTO.setPermissions(roleEntity.getPermissions().stream()
                .map(permission -> new PermissionConverter().entityToDTO(permission))
                .collect(Collectors.toList()));

        return roleDTO;
    }

    public RoleEntity dtoToEntity(RoleDTO roleDTO) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleDTO.getId());
        roleEntity.setName(roleDTO.getName());
        roleEntity.setPermissions(roleDTO.getPermissions().stream()
                .map(permission -> new PermissionConverter().dtoToEntity(permission))
                .collect(Collectors.toList()));

        return roleEntity;
    }
}
