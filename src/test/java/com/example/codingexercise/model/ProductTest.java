package com.example.codingexercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;
import org.junit.jupiter.api.Test;


class ProductTest {

    @Test
    void givenBuilderWithAllFields_whenBuild_thenAllFieldsAreSet() {
        // Given
        UUID id = UUID.randomUUID();
        ProductsPackage pkg = ProductsPackage.builder().packageName("pkg").build();

        // When
        Product product = Product.builder()
                .id(id)
                .productId("prod-1")
                .productPackage(pkg)
                .productName("Product")
                .productDescription("Desc")
                .usdPrice(10.5)
                .build();

        // Then
        assertEquals(id, product.getId());
        assertEquals("prod-1", product.getProductId());
        assertEquals(pkg, product.getProductPackage());
        assertEquals("Product", product.getProductName());
        assertEquals("Desc", product.getProductDescription());
        assertEquals(10.5, product.getUsdPrice());
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenEntityHasNullsAndZero() {
        // When
        Product product = new Product();

        // Then
        assertNull(product.getId());
        assertNull(product.getProductId());
        assertNull(product.getProductName());
        assertEquals(0.0, product.getUsdPrice());
    }

    @Test
    void givenProduct_whenSettersCalled_thenFieldsAreUpdated() {
        // Given
        Product product = new Product();

        // When
        product.setProductId("p1");
        product.setProductName("Name");
        product.setProductDescription("Desc");
        product.setUsdPrice(5.0);

        // Then
        assertEquals("p1", product.getProductId());
        assertEquals("Name", product.getProductName());
        assertEquals("Desc", product.getProductDescription());
        assertEquals(5.0, product.getUsdPrice());
    }

    @Test
    void givenTwoProductsWithSameProductIdNameDescriptionUsdPrice_whenEquals_thenTheyAreEqual() {
        // Given
        Product a = Product.builder()
                .productId("p1")
                .productName("A")
                .productDescription("D")
                .usdPrice(1.0)
                .build();
        Product same = Product.builder()
                .productId("p1")
                .productName("A")
                .productDescription("D")
                .usdPrice(1.0)
                .build();
        Product different = Product.builder()
                .productId("p2")
                .productName("B")
                .productDescription("D2")
                .usdPrice(2.0)
                .build();

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, different);
        assertEquals(a.hashCode(), same.hashCode());
    }

    @Test
    void givenProduct_whenEqualsWithNullOrOtherClass_thenReturnsFalse() {
        // Given
        Product product = Product.builder().productId("p1").build();

        // When / Then
        assertNotEquals(product, null);
        assertNotEquals(product, "string");
    }
}
