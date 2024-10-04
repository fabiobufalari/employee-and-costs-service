package com.bufalari.employee.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    private String name;
    private List<PermissionDTO> permissions;
}
