package com.example.productservice.services;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    @Override
    public String getProductById(Long id) {
        return "Product fetched from service with id: " + id;
    }

    @Override
    public List<String> getAllProducts() {
        return Collections.emptyList();
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public void addProduct() {

    }

    @Override
    public void updateProductById(Long id) {

    }
}
