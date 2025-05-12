package com.bufalari.employee.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User; // Use Spring Security User
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of AuditorAware to provide the current user's identifier.
 * Implementação de AuditorAware para fornecer o identificador do usuário atual.
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the identifier (e.g., username or UUID) of the current auditor.
     * Retorna o identificador (ex: username ou UUID) do auditor atual.
     *
     * @return Optional containing the current auditor's identifier, or Optional.empty() if none is available.
     *         Optional contendo o identificador do auditor atual, ou Optional.empty() se nenhum estiver disponível.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // Handle cases where there is no authenticated user (e.g., system processes, tests)
            // Trata casos onde não há usuário autenticado (ex: processos do sistema, testes)
            return Optional.of("system_employee"); // Or return Optional.empty() based on requirements
        }

        // Assuming the principal is the username string stored by JwtAuthFilter
        // Assumindo que o principal é a string do nome de usuário armazenada pelo JwtAuthFilter
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof User) {
           username = ((User) principal).getUsername();
        } else if (principal instanceof String) {
           username = (String) principal;
        } else {
            // Fallback if principal type is unexpected
            // Fallback se o tipo do principal for inesperado
             return Optional.of("unknown");
        }

        // Consider extracting User ID (UUID) if available in the token claims
        // Considere extrair o ID do Usuário (UUID) se disponível nas claims do token
        // String userId = extractUserIdFromPrincipal(principal); // Implement this logic if needed
        // return Optional.ofNullable(userId);

        return Optional.of(username);
    }

    // Example method to extract User ID (implement based on your token structure)
    // Método de exemplo para extrair User ID (implemente baseado na estrutura do seu token)
    /*
    private String extractUserIdFromPrincipal(Object principal) {
        if (principal instanceof YourCustomUserDetails) { // Replace with your actual UserDetails implementation if used
            return ((YourCustomUserDetails) principal).getId().toString();
        }
        // Add logic here if the user ID is stored differently in the Authentication principal
        // Adicione lógica aqui se o ID do usuário for armazenado de forma diferente no principal da Autenticação
        return null; // Or a default value
    }
    */
}