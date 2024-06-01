package com.example.productservice.security.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends BaseModel {
    private String name;
    private String email;
    private String hashedPassword;
    private List<Role> roles;
    private Boolean isEmailVerified;
}
