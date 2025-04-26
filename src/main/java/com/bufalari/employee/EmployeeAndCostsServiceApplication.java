package com.bufalari.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Moved to JpaAuditingConfig

/**
 * Main application class for Employee and Costs Management Service.
 * Classe principal da aplicação para o Serviço de Gerenciamento de Funcionários e Custos.
 */
@SpringBootApplication
@EnableFeignClients // Keep Feign for AuthServiceClient
// @EnableJpaAuditing // Auditing is now enabled in JpaAuditingConfig.java
public class EmployeeAndCostsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAndCostsServiceApplication.class, args);
	}
}