package com.example.productservice.security.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Token extends BaseModel {
    private String value;
    private User user;
    private Date expiryAt;
    private Boolean deleted;
}
