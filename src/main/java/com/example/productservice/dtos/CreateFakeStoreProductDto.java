package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakeStoreProductDto {
    private String title;
    private String description;
    private Long price;
    private String category;
    private String image;
}
