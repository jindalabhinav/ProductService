package com.example.productservice.thirdpartyclients;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Component
// This comes from a third party and usually comes direct as an Implementation
// and not an Interface because it won't have multiple implementations of the interface exposed
public class FakeStoreClient {
    private final RestTemplateBuilder restTemplateBuilder;
    private final String specificProductUrl;
    // @Value("${fakestore.api.url}") // this will also work and Constructor injection is not needed
    private final String genericProductUrl;

    @Autowired
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder,
                           @Value("${fakestore.api.url}") String genericProductUrl) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.genericProductUrl = genericProductUrl;
        this.specificProductUrl = genericProductUrl + "/{id}";
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

    public FakeStoreProductDto updateProductById(FakeStoreProductDto request) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Prepare the request entity with the product object
        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(request);

        // Execute the PUT request using exchange method
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                specificProductUrl, // URL endpoint
                HttpMethod.PUT, // HTTP method
                requestEntity, // Request entity containing the product object
                FakeStoreProductDto.class, // Expected response type
                request.getId() // Parameters sent for URL
        );

        // Check if the response body is null
        if (responseEntity.getBody() == null) {
            throw new ProductNotFoundException("No Product found to delete");
        }

        // Return the updated product DTO
        return responseEntity.getBody();
    }
}
