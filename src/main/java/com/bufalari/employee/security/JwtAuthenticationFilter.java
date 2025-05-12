package com.bufalari.employee.security;

import com.bufalari.employee.config.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException; // Importar exceções JWT
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor; // Usar Lombok
import org.slf4j.Logger; // Importar Logger
import org.slf4j.LoggerFactory; // Importar LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier; // Importar Qualifier
import org.springframework.lang.NonNull; // Usar @NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // Importar Interface
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para autenticação JWT. Valida o token em cada requisição.
 * Filter for JWT authentication. Validates the token on each request.
 */
@Component
@RequiredArgsConstructor // Injeta dependências finais
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil; // Serviço utilitário JWT
    // Injetar o UserDetailsService específico deste serviço pelo nome do bean
    @Qualifier("employeeUserDetailsService")
    private final UserDetailsService userDetailsService;

    /**
     * Processa cada requisição para validar o token JWT.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Verifica o header Authorization
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.trace("Authorization header missing or not Bearer type.");
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extrai o token
        jwt = authorizationHeader.substring(7);
        log.trace("Extracted JWT from header.");

        try {
            // 3. Extrai o username
            username = jwtUtil.extractUsername(jwt); // Pode lançar JwtException

            // 4. Se houver username e nenhuma autenticação no contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("Attempting to validate JWT for user: {}", username);
                // 5. Carrega UserDetails (usando o CustomUserDetailsService injetado)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 6. Valida o token
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    log.debug("JWT is valid for user {}. Setting authentication context.", username);
                    // 7. Cria token de autenticação do Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null, // Credenciais não usadas
                            userDetails.getAuthorities() // Permissões
                    );
                    // 8. Define detalhes da requisição
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 9. Define autenticação no contexto
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    log.warn("JWT token validation failed for user: {}", username);
                    SecurityContextHolder.clearContext(); // Limpar contexto se inválido
                }
            }

        } catch (ExpiredJwtException e) {
            log.warn("JWT token has expired: {}", e.getMessage());
            SecurityContextHolder.clearContext(); // Limpar contexto
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            SecurityContextHolder.clearContext(); // Limpar contexto
        } catch (Exception e) {
            log.error("Error during JWT filter processing: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext(); // Limpar contexto
        }

        // 10. Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }
}