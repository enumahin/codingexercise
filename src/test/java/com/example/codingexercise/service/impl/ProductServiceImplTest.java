package com.example.codingexercise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.codingexercise.dto.gateway.ProductDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private static final String PRODUCT_SERVICE_URL = "https://product-service.herokuapp.com/api/v1/products";

    @Mock
    private RestTemplate restTemplate;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(restTemplate, PRODUCT_SERVICE_URL);
    }

    @Test
    void givenProductId_whenGetProduct_thenReturnsProductFromRestTemplate() {
        // Given
        ProductDto expected = new ProductDto("id1", "Product", 10.0, 10.0);
        when(restTemplate.exchange(
                eq(PRODUCT_SERVICE_URL + "/{id}"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(ProductDto.class),
                eq("id1")))
                .thenReturn(ResponseEntity.ok(expected));

        // When
        ProductDto result = productService.getProduct("id1");

        // Then
        assertNotNull(result);
        assertEquals("id1", result.id());
        assertEquals("Product", result.name());
        assertEquals(10.0, result.usdPrice());
    }

    @Test
    void givenRestTemplateReturnsBody_whenGetProducts_thenReturnsList() {
        // Given
        List<ProductDto> body = List.of(
                new ProductDto("a", "A", 1.0, 1.0),
                new ProductDto("b", "B", 2.0, 2.0));
        when(restTemplate.exchange(
                eq(PRODUCT_SERVICE_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(body));

        // When
        List<ProductDto> result = productService.getProducts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("a", result.get(0).id());
        assertEquals("b", result.get(1).id());
    }

    @Test
    void givenRestTemplateReturnsNullBody_whenGetProducts_thenReturnsEmptyList() {
        // Given
        when(restTemplate.exchange(
                eq(PRODUCT_SERVICE_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        // When
        List<ProductDto> result = productService.getProducts();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void givenRestTemplateThrows_whenGetProducts_thenReturnsEmptyList() {
        // Given
        when(restTemplate.exchange(
                eq(PRODUCT_SERVICE_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("Network error"));

        // When
        List<ProductDto> result = productService.getProducts();

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }
}
