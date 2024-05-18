package com.example.inheritancedemo.joinedtable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "jt_user") // custom table name
// It will create all the columns from the Users class, and the tables for the subclasses will have only the columns specific to them
// The tables will be joined using the primary key of the parent class, present as a foreign key in the child class tables
@Inheritance(strategy = InheritanceType.JOINED)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String password;
}
