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
    Client --> User Service (Auth server)                               [Client requests authentication from User Service]
    UserService --Token--> Client                                       [User Service provides an authentication token to the Client]
    Client --Token--> Product Service (get resource)                    [Client uses the token to request a resource from Product Service]
    ProductService --Token--> UserService (/validate)                   [Product Service sends the token to User Service for validation]
    ProductService --if token validated--> Client (resource returned)   [If the token is validated, Product Service returns the requested resource to the Client]
 */