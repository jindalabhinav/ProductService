package com.example.productservice.controllers;

import com.example.productservice.models.Product;
import com.example.productservice.security.services.AuthenticationService;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
    public ProductController(ProductService productService,
            AuthenticationService authenticationService) {
        this.productService = productService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/paged")
    public Page<Product> getAllProducts(@RequestParam("pageNumber") int pageNumber,
                                        @RequestParam("pageSize") int pageSize) {
        return productService.getAllProducts(pageNumber, pageSize); // pageNumber is 0-based here
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

    @GetMapping("/users")
    public String getAllUsers() {
        // only for demo purpose, to show usage of service discovery, not supposed to be present in this class
        return productService.getAllUsers();
    }
}
