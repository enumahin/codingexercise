package com.example.codingexercise.service;

import com.example.codingexercise.dto.gateway.ProductDto;
import java.util.List;

/**
 * Abstraction over the external product service used to resolve product details.
 */
public interface ProductService {

    /**
     * Retrieves a product by its identifier from the backing product service.
     *
     * @param id product identifier
     * @return the matching product view
     */
    ProductDto getProduct(String id);

    /**
     * Retrieves all products from the backing product service.
     *
     * @return list of products
     */
    List<ProductDto> getProducts();
}
