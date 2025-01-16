package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.thirdpartyclients.FakeStoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("FakeProductService")
public class FakeStoreProductService implements ProductService {
    private final FakeStoreClient fakeStoreClient;

    @Autowired
    public FakeStoreProductService(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @Override
    public Product getProductById(Long id) {
        return getProductFromFakeStoreProductDto(fakeStoreClient.getProductById(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return Arrays.stream(fakeStoreClient.getAllProducts())
                .map(this::getProductFromFakeStoreProductDto)
                .toList();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return getProductFromFakeStoreProductDto(fakeStoreClient.deleteProductById(id));
    }

    @Override
    public Product addProduct(Product request) {
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductDtoFromProduct(request);
        return getProductFromFakeStoreProductDto(fakeStoreClient.addProduct(fakeStoreProductDto));
    }

    @Override
    public Product updateProduct(Product request) {
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductDtoFromProduct(request);
        return getProductFromFakeStoreProductDto(fakeStoreClient.updateProductById(fakeStoreProductDto));
    }

    @Override
    public String getAllUsers() {
        return "";
    }

    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDto getFakeStoreProductDtoFromProduct(Product request) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(request.getId());
        fakeStoreProductDto.setDescription(request.getDescription());
        fakeStoreProductDto.setPrice(request.getPrice());
        fakeStoreProductDto.setCategory(request.getCategory().getName());
        fakeStoreProductDto.setTitle(request.getTitle());
        return fakeStoreProductDto;
    }
}
