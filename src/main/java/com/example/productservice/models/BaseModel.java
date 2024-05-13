package com.example.productservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass // we don't want a table to be created for BaseModel
public abstract class BaseModel {
    @Id
    private Long id;
}
