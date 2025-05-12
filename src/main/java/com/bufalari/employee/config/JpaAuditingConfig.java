package com.bufalari.employee.config;

import com.bufalari.employee.auditing.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// Habilita auditoria JPA e especifica qual bean AuditorAware usar
@EnableJpaAuditing(auditorAwareRef = "auditorProviderEmployee") // <<< Nome único para este serviço
public class JpaAuditingConfig {

    // Define o bean AuditorAware com um nome específico
    @Bean
    public AuditorAware<String> auditorProviderEmployee() { // <<< Nome único para este serviço
        return new AuditorAwareImpl(); // Retorna a implementação local
    }
}