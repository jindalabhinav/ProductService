package com.example.productservice.services;

import org.springframework.stereotype.Service;

import java.util.List;


/*
Should this be an interface?
Yes, this would otherwise violate the Dependency Inversion Principle
 */
public interface ProductService {
    String getProductById(Long id);
    List<String> getAllProducts();
    void deleteProductById(Long id);
    void addProduct();
    void updateProductById(Long id);
}
