package com.example.productservice.services;

import com.example.productservice.models.Product;

import java.util.List;

import org.springframework.data.domain.Page;


/*
Should this be an interface?
Yes, this would otherwise violate the Dependency Inversion Principle
 */
public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Page<Product> getAllProducts(int pageNumber, int pageSize);
    Product deleteProductById(Long id);
    Product addProduct(Product request);
    Product updateProduct(Product product);

    String getAllUsers();
}

