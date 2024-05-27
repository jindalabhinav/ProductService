package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("ProductService")
@Primary
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ProductNotFoundException("Product not found for id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return products;
    }

    @Override
    public Product deleteProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            throw new ProductNotFoundException("Product with Id: " + id + " doesn't exist.");
        productRepository.deleteById(id);
        return product.get();
    }

    @Override
    public Product addProduct(Product request) {
        // Not required since we're using CASCADING now
//        Optional<Category> category = categoryRepository.findByName(request.getCategory().getName());
//        if (category.isEmpty()) {
//            Category createdCategory = categoryRepository.save(request.getCategory());
//            request.setCategory(createdCategory);
//        }
//        else {
//            request.setCategory(category.get());
//        }
        return productRepository.save(request);
    }

    @Override
    public Product updateProduct(Product request) {
        Optional<Product> product = productRepository.findById(request.getId());
        if (product.isEmpty())
            throw new ProductNotFoundException("Product with Id: " + request.getId() + " doesn't exist.");
        return productRepository.save(request);
    }
}
