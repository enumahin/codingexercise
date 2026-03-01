package com.example.codingexercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;


class ProductsPackageTest {

    @Test
    void givenBuilderWithAllFields_whenBuild_thenAllFieldsAreSet() {
        // Given
        UUID id = UUID.randomUUID();
        Product product = Product.builder().productId("p1").build();

        // When
        ProductsPackage pkg = ProductsPackage.builder()
                .packageId(id)
                .packageName("Package")
                .packageDescription("Desc")
                .priceCurrency("USD")
                .products(Set.of(product))
                .packagePrice(99.99)
                .exchangeRate(1.0)
                .build();

        // Then
        assertEquals(id, pkg.getPackageId());
        assertEquals("Package", pkg.getPackageName());
        assertEquals("Desc", pkg.getPackageDescription());
        assertEquals("USD", pkg.getPriceCurrency());
        assertEquals(1, pkg.getProducts().size());
        assertTrue(pkg.getProducts().contains(product));
        assertEquals(99.99, pkg.getPackagePrice());
        assertEquals(1.0, pkg.getExchangeRate());
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenHasDefaultEmptyProductsSet() {
        // When
        ProductsPackage pkg = new ProductsPackage();

        // Then
        assertNotNull(pkg.getProducts());
        assertTrue(pkg.getProducts().isEmpty());
    }

    @Test
    void givenProductsPackage_whenSettersCalled_thenFieldsAreUpdated() {
        // Given
        ProductsPackage pkg = new ProductsPackage();

        // When
        pkg.setPackageName("Name");
        pkg.setPackageDescription("Desc");
        pkg.setPriceCurrency("GBP");
        pkg.setPackagePrice(50.0);
        pkg.setExchangeRate(0.8);

        // Then
        assertEquals("Name", pkg.getPackageName());
        assertEquals("Desc", pkg.getPackageDescription());
        assertEquals("GBP", pkg.getPriceCurrency());
        assertEquals(50.0, pkg.getPackagePrice());
        assertEquals(0.8, pkg.getExchangeRate());
    }

    @Test
    void givenTwoPackagesWithSamePackageFields_whenEquals_thenTheyAreEqual() {
        // Given
        ProductsPackage a = ProductsPackage.builder()
                .packageName("A")
                .packageDescription("D")
                .priceCurrency("USD")
                .packagePrice(1.0)
                .exchangeRate(1.0)
                .build();
        ProductsPackage same = ProductsPackage.builder()
                .packageName("A")
                .packageDescription("D")
                .priceCurrency("USD")
                .packagePrice(1.0)
                .exchangeRate(1.0)
                .build();
        ProductsPackage different = ProductsPackage.builder()
                .packageName("B")
                .packagePrice(2.0)
                .build();

        // When / Then
        assertEquals(a, same);
        assertNotEquals(a, different);
        assertEquals(a.hashCode(), same.hashCode());
    }
}
