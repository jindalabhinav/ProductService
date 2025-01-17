package com.example.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category extends BaseModel implements Serializable {
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    // But we also have ManyToOne in Product class, so this is a bidirectional relationship
    // This can cause a confusion in Hibernate, for which we need to specify the mappedBy attribute
    @JsonBackReference // When a Category is serialized, it will not include its Products, this is to prevent infinite recursion while getting Products
    private List<Product> products;
}

/*
Get Category by ID
Get Category by Id Join Products where Category ID = :id
 */