package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.service.ProductServiceGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of {@link ProductServiceGateway} that delegates to a {@link RestTemplate}.
 */
@Service
public class ProductServiceGatewayImpl implements ProductServiceGateway {

    private final RestTemplate restTemplate;

    /**
     * Creates a new gateway using the provided {@link RestTemplate}.
     *
     * @param restTemplate HTTP client used to call the product service
     */
    public ProductServiceGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto getProduct(String id) {
        return restTemplate.getForObject("https://product-service.herokuapp.com/api/v1/products/{id}",
                ProductDto.class, id);
    }
}
