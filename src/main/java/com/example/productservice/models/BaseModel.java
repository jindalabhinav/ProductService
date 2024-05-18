package com.example.productservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass // we don't want a table to be created for BaseModel
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // It instructs the JPA provider to choose the appropriate strategy for generating the ID
    // based on the underlying database system, whatever it supports
    private Long id;
}
