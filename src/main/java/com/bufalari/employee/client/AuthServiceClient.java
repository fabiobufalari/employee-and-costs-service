package com.bufalari.employee.client;

import com.bufalari.employee.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicação com o serviço de autenticação.
 * Feign client for communication with the authentication service.
 */
@FeignClient(name = "auth-service", url = "${auth.service.url:http://localhost:8081}")
public interface AuthServiceClient {

    /**
     * Busca os detalhes do usuário por nome de usuário.
     * Retrieves user details by username.
     */
    @GetMapping("/api/users/{username}")
    UserDetailsDTO getUserByUsername(@PathVariable("username") String username);
}