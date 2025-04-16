package com.bufalari.employee.config;

import com.bufalari.employee.entity.RoleEntity;
import com.bufalari.employee.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;



/**
 * Inicializa papéis padrão no banco de dados.
 * Initializes default roles in the database.
 */
@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("MANAGER");
        createRoleIfNotExists("WORKER");
        createRoleIfNotExists("OWNER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }
}