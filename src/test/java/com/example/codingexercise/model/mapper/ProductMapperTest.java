package com.example.codingexercise.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.model.Product;
import org.junit.jupiter.api.Test;


class ProductMapperTest {

    private final ProductMapper mapper = ProductMapper.INSTANCE;

    @Test
    void givenProduct_whenToDto_thenMapsToProductDto() {
        // Given
        Product product = Product.builder()
                .productId("p1")
                .productName("Product")
                .productDescription("Desc")
                .usdPrice(10.0)
                .build();

        // When
        ProductDto dto = mapper.toDto(product);

        // Then
        assertEquals("p1", dto.getProductId());
        assertEquals("Product", dto.getProductName());
        assertEquals("Desc", dto.getProductDescription());
        assertEquals(10.0, dto.getUsdPrice());
    }

    @Test
    void givenProductDto_whenToEntity_thenMapsToProduct() {
        // Given
        ProductDto dto = ProductDto.builder()
                .productId("p2")
                .productName("Other")
                .productDescription("D2")
                .usdPrice(5.5)
                .build();

        // When
        Product product = mapper.toEntity(dto);

        // Then
        assertEquals("p2", product.getProductId());
        assertEquals("Other", product.getProductName());
        assertEquals("D2", product.getProductDescription());
        assertEquals(5.5, product.getUsdPrice());
    }

    @Test
    void givenProduct_whenToDtoThenToEntity_thenPreservesProductIdNameDescriptionUsdPrice() {
        // Given
        Product product = Product.builder()
                .productId("id")
                .productName("N")
                .productDescription("D")
                .usdPrice(1.0)
                .build();

        // When
        ProductDto dto = mapper.toDto(product);
        Product back = mapper.toEntity(dto);

        // Then
        assertEquals(product.getProductId(), back.getProductId());
        assertEquals(product.getProductName(), back.getProductName());
        assertEquals(product.getProductDescription(), back.getProductDescription());
        assertEquals(product.getUsdPrice(), back.getUsdPrice());
    }
}
