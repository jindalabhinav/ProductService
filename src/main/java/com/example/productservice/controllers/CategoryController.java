package com.example.productservice.controllers;

import com.example.productservice.models.Category;
import com.example.productservice.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
