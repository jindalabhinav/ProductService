package com.example.productservice.thirdpartyclients;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreClient {
    private final RestTemplateBuilder restTemplateBuilder;
    private final String specificProductUrl = "https://fakestoreapi.com/products/{id}";
    private final String genericProductUrl = "https://fakestoreapi.com/products";

    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity =  restTemplate
                .getForEntity(specificProductUrl, FakeStoreProductDto.class, id);

        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductDto[] getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate
                .getForEntity(genericProductUrl, FakeStoreProductDto[].class);

        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No Products found");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductDto deleteProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback =
                restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class); // Sets the Accept Header in the request to FakeStoreProductDto
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor =
                restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> responseEntity =
                restTemplate.execute(specificProductUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);
        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No Product found to delete");
        }
        return responseEntity.getBody();
    }

    public FakeStoreProductDto addProduct(FakeStoreProductDto request) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate
                .postForEntity(genericProductUrl, request, FakeStoreProductDto.class);
        return responseEntity.getBody();
    }

    public void updateProductById(Long id) {

    }
}
