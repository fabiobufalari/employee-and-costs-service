package com.bufalari.employee.service;


import com.bufalari.employee.convert.RoleConverter;
import com.bufalari.employee.dto.RoleDTO;
import com.bufalari.employee.entity.RoleEntity;
import com.bufalari.employee.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    public RoleService(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleEntity roleEntity = roleConverter.dtoToEntity(roleDTO);
        RoleEntity savedRole = roleRepository.save(roleEntity);
        return roleConverter.entityToDTO(savedRole);
    }

    public Optional<RoleDTO> getRoleById(Long id) {
        return roleRepository.findById(id).map(roleConverter::entityToDTO);
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream().map(roleConverter::entityToDTO).collect(Collectors.toList());
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role not found");
        }
        RoleEntity roleEntity = roleConverter.dtoToEntity(roleDTO);
        roleEntity.setId(id);
        RoleEntity updatedRole = roleRepository.save(roleEntity);
        return roleConverter.entityToDTO(updatedRole);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
