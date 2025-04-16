package com.bufalari.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Classe principal da aplicação Employee Role Access Management.
 * Main application class for Employee Role Access Management.
 */
@SpringBootApplication
@EnableFeignClients
public class EmployeeRoleAccessManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeRoleAccessManagementApplication.class, args);
    }
}