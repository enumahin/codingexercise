package com.example.codingexercise.dto.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class ProductDtoTest {

    @Test
    void givenComponents_whenConstruct_thenAllAccessorsReturnValues() {
        // When
        ProductDto dto = new ProductDto("id1", "Product", 10.0, 8.5);

        // Then
        assertEquals("id1", dto.id());
        assertEquals("Product", dto.name());
        assertEquals(10.0, dto.usdPrice());
        assertEquals(8.5, dto.localPrice());
    }

    @Test
    void givenTwoRecordsWithSameComponents_whenEquals_thenTheyAreEqual() {
        // Given
        ProductDto a = new ProductDto("id", "Name", 1.0, 0.9);
        ProductDto same = new ProductDto("id", "Name", 1.0, 0.9);
        ProductDto different = new ProductDto("id2", "Other", 2.0, 1.8);

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, different);
        assertEquals(a.hashCode(), same.hashCode());
    }

    @Test
    void givenProductDto_whenEqualsWithNullOrOtherClass_thenReturnsFalse() {
        // Given
        ProductDto dto = new ProductDto("id", "n", 0.0, 0.0);

        // When / Then
        assertNotEquals(null, dto);
        assertEquals(false, dto.equals("other"));
    }
}
