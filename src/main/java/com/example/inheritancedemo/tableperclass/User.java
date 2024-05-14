package com.example.inheritancedemo.tableperclass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tpc_user") // custom table name
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // It will create tables for each class, of the parent class and the child classes

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
}
