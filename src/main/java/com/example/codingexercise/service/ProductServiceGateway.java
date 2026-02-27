package com.example.codingexercise.service;

import com.example.codingexercise.dto.ProductDto;

/**
 * Abstraction over the external product service used to resolve product details.
 */
public interface ProductServiceGateway {

    /**
     * Retrieves a product by its identifier from the backing product service.
     *
     * @param id product identifier
     * @return the matching product view
     */
    ProductDto getProduct(String id);
}
