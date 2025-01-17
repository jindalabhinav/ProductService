package com.example.productservice.controllers;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

//    @Autowired
//    //@InjectMocks
//    private ProductController productController;
//
//    @MockBean
//    //@Mock
//    private ProductService productService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void getProductById() throws AccessDeniedException {
//        // Arrange
//        Product dummy = new Product();
//        dummy.setId(1L);
//        dummy.setTitle("dummy");
//        when(productService.getProductById(1L)).thenReturn(dummy);
//
//        // Act
//        Product p = productController.getProductById("", 1L);
//
//        // Assert
//        assertEquals(1L, p.getId());
//    }
//
//    @Test
//    void getProductByIdThrowsException() {
//        // Arrange
//        when(productService.getProductById(1L)).thenThrow(new ProductNotFoundException("Product not found"));
//
//        // Act and Assert
//        assertThrows(ProductNotFoundException.class, () -> productController.getProductById("", 1L));
//    }
}