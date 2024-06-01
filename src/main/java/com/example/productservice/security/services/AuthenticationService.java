package com.example.productservice.security.services;

import org.flywaydb.core.internal.parser.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {
    private RestTemplate restTemplate;
    private final String url = "http://localhost:9000/users/validateToken/{token}";

    public AuthenticationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean authenticate(String token) {
        ResponseEntity<Token> responseEntity = restTemplate.getForEntity(url, Token.class, token);
        return responseEntity.getBody() != null;
    }
}

/*
Client --> User Service (Auth server)
UserService --> Token --> Client
Client --Token--> User Service /validate
 */
