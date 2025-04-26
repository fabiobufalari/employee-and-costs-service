package com.bufalari.employee.config;

import com.bufalari.employee.auditing.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class to enable JPA Auditing.
 * Classe de configuração para habilitar a Auditoria JPA.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") // Specify the bean name for AuditorAware
public class JpaAuditingConfig {

    /**
     * Provides the AuditorAware bean implementation.
     * Fornece a implementação do bean AuditorAware.
     *
     * @return An instance of AuditorAware<String>.
     *         Uma instância de AuditorAware<String>.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
}