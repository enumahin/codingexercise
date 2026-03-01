package com.example.codingexercise.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class ProductDtoTest {

    @Test
    void givenBuilderWithAllFields_whenBuild_thenAllFieldsAreSet() {
        // Given
        ProductsPackageDto pkg = ProductsPackageDto.builder().packageName("pkg").build();

        // When
        ProductDto dto = ProductDto.builder()
                .id("id1")
                .productId("prod-1")
                .productPackage(pkg)
                .productName("Product")
                .productDescription("Desc")
                .usdPrice(10.5)
                .build();

        // Then
        assertEquals("id1", dto.getId());
        assertEquals("prod-1", dto.getProductId());
        assertEquals(pkg, dto.getProductPackage());
        assertEquals("Product", dto.getProductName());
        assertEquals("Desc", dto.getProductDescription());
        assertEquals(10.5, dto.getUsdPrice());
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenDtoHasNullAndZeroDefaults() {
        // When
        ProductDto dto = new ProductDto();

        // Then
        assertEquals(null, dto.getId());
        assertEquals(null, dto.getProductId());
        assertEquals(null, dto.getProductName());
        assertEquals(0.0, dto.getUsdPrice());
    }

    @Test
    void givenTwoDtosWithSameProductIdNameDescriptionUsdPrice_whenEquals_thenTheyAreEqual() {
        // Given
        ProductDto a = ProductDto.builder()
                .productId("p1")
                .productName("A")
                .productDescription("D")
                .usdPrice(1.0)
                .build();
        ProductDto same = ProductDto.builder()
                .productId("p1")
                .productName("A")
                .productDescription("D")
                .usdPrice(1.0)
                .build();
        ProductDto differentPrice = ProductDto.builder()
                .productId("p1")
                .productName("A")
                .productDescription("D")
                .usdPrice(2.0)
                .build();

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, differentPrice);
        assertEquals(a.hashCode(), same.hashCode());
        assertNotEquals(a.hashCode(), differentPrice.hashCode());
    }

    @Test
    void givenProductDto_whenEqualsWithNullOrOtherClass_thenReturnsFalse() {
        // Given
        ProductDto dto = ProductDto.builder().productId("p1").build();

        // When / Then
        assertEquals(false, dto.equals(null));
        assertEquals(false, dto.equals("not a ProductDto"));
    }
}
