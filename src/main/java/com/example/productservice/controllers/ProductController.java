package com.example.productservice.controllers;

import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

// We need to tell Spring to create this Class' object
// We're using a RestController and not a normal @Controller because we want Spring to treat this as a REST Controller and appl
// and apply any compile time validations for REST
@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(@Qualifier("ProductService") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<String> getAllProducts() {
        return productService.getAllProducts();
    }

//    @GetMapping("/products/{")
//    public List<String> getProductByCategory(String category) {
//
//    }
}
