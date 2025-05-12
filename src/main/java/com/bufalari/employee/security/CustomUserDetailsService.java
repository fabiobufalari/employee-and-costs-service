package com.bufalari.employee.security;

import com.bufalari.employee.client.AuthServiceClient;
import com.bufalari.employee.dto.UserDetailsDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service("employeeUserDetailsService") // Nome explícito do bean
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final AuthServiceClient authServiceClient;

    @Autowired
    public CustomUserDetailsService(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user details for username: {}", username);
        UserDetailsDTO userDetailsDTO;
        try {
            userDetailsDTO = authServiceClient.getUserByUsername(username);

            if (userDetailsDTO == null) {
                log.warn("User details DTO received from AuthServiceClient is null for username: {}", username);
                throw new UsernameNotFoundException("User not found: " + username + " (Auth service returned null)");
            }

            String passwordPlaceholder = ""; 

            log.info("Successfully loaded user details via auth service for username: {}", userDetailsDTO.getUsername());
            return new User(
                    userDetailsDTO.getUsername(),
                    passwordPlaceholder, 
                    userDetailsDTO.getRoles() != null ?
                            userDetailsDTO.getRoles().stream()
                                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                                    .collect(Collectors.toList())
                            : Collections.emptyList()
            );
        } catch (FeignException.NotFound e) {
            log.warn("User not found via auth service (Feign 404) for username: {}", username, e);
            throw new UsernameNotFoundException("User not found: " + username, e);
        } catch (FeignException e) {
            log.error("Feign error calling authentication service for username: {}. Status: {}, Message: {}", username, e.status(), e.getMessage(), e);
            throw new UsernameNotFoundException("Failed to load user details (auth service communication error) for user: " + username, e);
        } catch (Exception e) {
            log.error("Unexpected error loading user details for username: " + username, e);
            throw new UsernameNotFoundException("Unexpected error loading user details for user: " + username + ". Cause: " + e.getMessage(), e);
        }
    }
}