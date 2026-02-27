package com.example.codingexercise.service;

import com.example.codingexercise.dto.ProductDto;

public interface ProductServiceGateway {

    ProductDto getProduct(String id);
}
