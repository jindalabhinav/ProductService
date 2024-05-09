package com.example.productservice.services;

import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long id) {
        return new Product();
    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.emptyList();
    }

    @Override
    public Product deleteProductById(Long id) {
return null;
    }

    @Override
    public Product addProduct(Product request) {

        return null;
    }

    @Override
    public void updateProductById(Long id) {

    }
}
