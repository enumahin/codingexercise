package com.example.codingexercise.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.model.ProductsPackage;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;


class ProductsPackageMapperTest {

    private ProductsPackageMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ProductsPackageMapper.class);
    }

    @Test
    void givenProductsPackage_whenToDto_thenMapsToDto() {
        // Given
        UUID id = UUID.randomUUID();
        ProductsPackage entity = ProductsPackage.builder()
                .packageId(id)
                .packageName("Package")
                .packageDescription("Desc")
                .priceCurrency("USD")
                .packagePrice(99.0)
                .exchangeRate(1.0)
                .build();
        entity.setCreatedBy("user");
        entity.setVoided(false);
        entity.setUuid("uuid-1");

        // When
        ProductsPackageDto dto = mapper.toDto(entity);

        // Then
        assertEquals(id.toString(), dto.getPackageId());
        assertEquals("Package", dto.getPackageName());
        assertEquals("Desc", dto.getPackageDescription());
        assertEquals("USD", dto.getPriceCurrency());
        assertEquals(99.0, dto.getPackagePrice());
        assertEquals(1.0, dto.getExchangeRate());
        assertEquals("user", dto.getCreatedBy());
        assertFalse(dto.isVoided());
        assertEquals("uuid-1", dto.getUuid());
        assertNotNull(dto.getProducts());
        assertTrue(dto.getProducts().isEmpty());
    }

    @Test
    void givenProductsPackageDto_whenToEntity_thenMapsToEntity() {
        // Given
        ProductsPackageDto dto = ProductsPackageDto.builder()
                .packageName("Pkg")
                .packageDescription("Desc")
                .priceCurrency("GBP")
                .packagePrice(50.0)
                .exchangeRate(0.8)
                .build();

        // When
        ProductsPackage entity = mapper.toEntity(dto);

        // Then
        assertEquals("Pkg", entity.getPackageName());
        assertEquals("Desc", entity.getPackageDescription());
        assertEquals("GBP", entity.getPriceCurrency());
        assertEquals(50.0, entity.getPackagePrice());
        assertEquals(0.8, entity.getExchangeRate());
        assertNotNull(entity.getProducts());
        assertTrue(entity.getProducts().isEmpty());
    }

    @Test
    void givenProductsPackage_whenToDtoThenToEntity_thenPreservesPackageFields() {
        // Given
        ProductsPackage entity = ProductsPackage.builder()
                .packageId(UUID.randomUUID())
                .packageName("R")
                .packageDescription("D")
                .priceCurrency("EUR")
                .packagePrice(10.0)
                .exchangeRate(1.2)
                .build();

        // When
        ProductsPackageDto dto = mapper.toDto(entity);
        ProductsPackage back = mapper.toEntity(dto);

        // Then
        assertEquals(entity.getPackageName(), back.getPackageName());
        assertEquals(entity.getPackageDescription(), back.getPackageDescription());
        assertEquals(entity.getPriceCurrency(), back.getPriceCurrency());
        assertEquals(entity.getPackagePrice(), back.getPackagePrice());
        assertEquals(entity.getExchangeRate(), back.getExchangeRate());
    }
}
