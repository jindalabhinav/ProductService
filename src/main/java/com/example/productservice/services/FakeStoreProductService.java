package com.example.productservice.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("FakeProductService")
public class FakeStoreProductService implements ProductService {
    @Override
    public String getProductById(Long id) {
        return "Product fetched from fake service with id: " + id;
    }

    @Override
    public List<String> getAllProducts() {
        return null;
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
