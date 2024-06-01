package com.example.productservice.controllers;

import com.example.productservice.models.Product;
import com.example.productservice.security.services.AuthenticationService;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

// We need to tell Spring to create this Class' object
// We're using a RestController and not a normal @Controller because we want Spring to treat this as a REST Controller
// and apply any compile time validations for REST
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ProductController(@Qualifier("ProductService") ProductService productService,
            AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@RequestHeader("Authorization") String token,
                                  @PathVariable("id") Long id) throws AccessDeniedException {
        if (!authenticationService.authenticate(token)) {
            throw new AccessDeniedException("You're not authorized to perform this operation");
        }
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product request) {
        return productService.addProduct(request);
    }

    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProductById(id);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product request) {
        return productService.updateProduct(request);
    }
}
