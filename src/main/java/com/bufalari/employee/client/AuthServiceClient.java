package com.bufalari.employee.client; // Pacote correto

import com.bufalari.employee.dto.UserDetailsDTO; // Pacote correto
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicação com o serviço de autenticação.
 * Feign client for communication with the authentication service.
 */
// URL base virá do application.yml (ex: http://localhost:8083/api)
@FeignClient(name = "auth-service-employee", url = "${auth.service.url}") // Nome único opcional, URL correta via application.yml
public interface AuthServiceClient {

    /**
     * Busca os detalhes do usuário por nome de usuário no serviço de autenticação.
     * Retrieves user details by username from the authentication service.
     * O path aqui é relativo à URL base definida acima.
     * The path here is relative to the base URL defined above.
     */
    // <<< AJUSTE NO PATH: Deve corresponder ao endpoint no Auth Controller RELATIVO à URL base >>>
    @GetMapping("/users/username/{username}")
    UserDetailsDTO getUserByUsername(@PathVariable("username") String username);

    // Se precisar buscar por ID futuramente:
    /*
    @GetMapping("/users/{id}") // Assumindo endpoint GET /api/users/{id} no Auth Service
    UserDetailsDTO getUserById(@PathVariable("id") String userId); // Passar UUID como String
    */
}