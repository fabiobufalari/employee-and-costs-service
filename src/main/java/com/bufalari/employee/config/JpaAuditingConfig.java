package com.bufalari.employee.config;

import com.bufalari.employee.auditing.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // Ref é "auditorProvider"
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() { // Nome do bean é "auditorProvider"
        return new AuditorAwareImpl();
    }
}