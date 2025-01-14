package com.example.productservice.security.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/products/{id}").hasAuthority("CUSTOMER") // Only users with CUSTOMER authority can access /products/{id}
                .requestMatchers("/products").hasAuthority("ADMIN") // Only users with ADMIN authority can access /products
                .anyRequest().authenticated() // Require all requests to be authenticated
            )
            .csrf().disable()
            .cors().disable()
            .oauth2ResourceServer(oauth2 -> oauth2 // Configure the application as an OAuth2 Resource Server
                .jwt(jwt -> jwt // Use JWT for authentication
                    .jwtAuthenticationConverter(jwtAuthenticationConverter()) // Use the custom JwtAuthenticationConverter
                )
            );
        
        return http.build(); // Build the HttpSecurity instance
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("ServiceRole");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
