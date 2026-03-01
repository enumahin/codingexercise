package com.example.codingexercise.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;


class ProductsPackageDtoTest {

    @Test
    void givenBuilderWithAllFields_whenBuild_thenAllFieldsAreSet() {
        // Given
        ProductDto product = ProductDto.builder().productId("p1").productName("P").build();

        // When
        ProductsPackageDto dto = ProductsPackageDto.builder()
                .packageId("pkg-id")
                .packageName("Package")
                .packageDescription("Desc")
                .priceCurrency("USD")
                .products(Set.of(product))
                .packagePrice(99.99)
                .exchangeRate(1.0)
                .build();

        // Then
        assertEquals("pkg-id", dto.getPackageId());
        assertEquals("Package", dto.getPackageName());
        assertEquals("Desc", dto.getPackageDescription());
        assertEquals("USD", dto.getPriceCurrency());
        assertEquals(Set.of(product), dto.getProducts());
        assertEquals(99.99, dto.getPackagePrice());
        assertEquals(1.0, dto.getExchangeRate());
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenDtoHasEmptyProductsSet() {
        // When
        ProductsPackageDto dto = new ProductsPackageDto();

        // Then
        assertNotNull(dto.getProducts());
        assertTrue(dto.getProducts().isEmpty());
    }

    @Test
    void givenDto_whenSettersCalled_thenFieldsAreUpdated() {
        // Given
        ProductsPackageDto dto = new ProductsPackageDto();

        // When
        dto.setPackageId("id");
        dto.setPackageName("Name");
        dto.setPackageDescription("Desc");
        dto.setPriceCurrency("GBP");
        dto.setPackagePrice(50.0);
        dto.setExchangeRate(0.8);

        // Then
        assertEquals("id", dto.getPackageId());
        assertEquals("Name", dto.getPackageName());
        assertEquals("Desc", dto.getPackageDescription());
        assertEquals("GBP", dto.getPriceCurrency());
        assertEquals(50.0, dto.getPackagePrice());
        assertEquals(0.8, dto.getExchangeRate());
    }

    @Test
    void givenDto_whenSetProducts_thenProductsSetIsReplaced() {
        // Given
        ProductsPackageDto dto = new ProductsPackageDto();
        ProductDto p = ProductDto.builder().productId("p1").build();

        // When
        dto.setProducts(Set.of(p));

        // Then
        assertEquals(1, dto.getProducts().size());
        assertTrue(dto.getProducts().contains(p));
    }
}
