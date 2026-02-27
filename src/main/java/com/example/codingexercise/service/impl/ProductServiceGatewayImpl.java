package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.service.ProductServiceGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductServiceGatewayImpl implements ProductServiceGateway {

    private final RestTemplate restTemplate;

    public ProductServiceGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductDto getProduct(String id) {
        return restTemplate.getForObject("https://product-service.herokuapp.com/api/v1/products/{id}",
                ProductDto.class, id);
    }
}
