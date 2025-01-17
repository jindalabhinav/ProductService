package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("ProductService")
@Primary
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private RestTemplate restTemplate;
    private RedisTemplate<Long, Object> redisTemplate;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, RestTemplate restTemplate, RedisTemplate<Long, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        /*
        Cache Implementation

        1. Check if the product is present in the cache
        2. If present, return the product
        3. If not present, fetch the product from the database
        4. Store the product in the cache
        5. Return the product
         */
        if (redisTemplate.opsForHash().get(id, "PRODUCTS") != null) {
            System.out.println("Cache Hit");
            return (Product) redisTemplate.opsForHash().get(id, "PRODUCTS");
        }
        System.out.println("Cache Miss");
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            redisTemplate.opsForHash().put(id, "PRODUCTS", product.get());
        }
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
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> productsPage = productRepository.findAll(pageable);
        if (productsPage.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        return productsPage;
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
        Optional<Category> category = categoryRepository.findByName(request.getCategory().getName());
        if (category.isEmpty()) {
            Category createdCategory = categoryRepository.save(request.getCategory());
            request.setCategory(createdCategory);
        }
        else {
            request.setCategory(category.get());
        }
        return productRepository.save(request);
    }

    @Override
    public Product updateProduct(Product request) {
        Optional<Product> product = productRepository.findById(request.getId());
        if (product.isEmpty())
            throw new ProductNotFoundException("Product with Id: " + request.getId() + " doesn't exist.");
        return productRepository.save(request);
    }

    @Override
    public String getAllUsers() {
        // only for demo purpose, to show usage of service discovery
        ResponseEntity<String> response = restTemplate.getForEntity("http://userservice/users", String.class);
        return response.getBody();
    }
}
