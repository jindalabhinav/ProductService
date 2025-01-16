package com.example.productservice.services;

import com.example.productservice.models.Product;

import java.util.List;


/*
Should this be an interface?
Yes, this would otherwise violate the Dependency Inversion Principle
 */
public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product deleteProductById(Long id);
    Product addProduct(Product request);
    Product updateProduct(Product product);

    String getAllUsers();
}

