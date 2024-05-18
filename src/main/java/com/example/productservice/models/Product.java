package com.example.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product extends BaseModel {
    private String title;
    private String description;
    private Long price;
    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonManagedReference // This means that when a Product is serialized, it will include its Category
    private Category category; // Many Products can have 1 Category
    /*
        1 Product --> 1 Category
        1 Category --> Many Products
     */
}
