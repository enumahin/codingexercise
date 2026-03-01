package com.example.codingexercise.dto.gateway;

/**
 * Read-only view of a product used when composing a package.
 *
 * @param id         unique identifier of the product
 * @param name       display name of the product
 * @param usdPrice   price of the product in USD
 * @param localPrice price of the product in the selected currency
 */
public record ProductDto(String id, String name, double usdPrice, double localPrice) {
}
