package com.example.codingexercise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.exception.ResourceNotFoundException;
import com.example.codingexercise.integration.AbstractionContainerBaseTest;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.PackageService;
import jakarta.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;


@WithMockUser(username = "user", password = "pass", roles = "USER")
class PackageServiceIntegrationTest extends AbstractionContainerBaseTest {

    @Autowired
    private PackageService packageService;

    @Autowired
    private PackageRepository packageRepository;

    @BeforeEach
    void setUp() {
        packageRepository.deleteAll();
    }

    private static ProductsPackageDto newPackageDto(String name) {
        return ProductsPackageDto.builder()
                .packageName(name)
                .packageDescription("Description for " + name)
                .priceCurrency("USD")
                .packagePrice(100.0)
                .exchangeRate(1.0)
                .products(Set.of(
                        ProductDto.builder()
                                .productId("prod-1")
                                .productName("Product One")
                                .productDescription("Desc")
                                .usdPrice(50.0)
                                .build()))
                .build();
    }

    @Test
    @Transactional
    void givenPackageDto_whenCreatePackage_thenReturnsCreatedPackageAndCanBeRetrievedById() {
        // Given
        ProductsPackageDto input = newPackageDto("Integration Pkg 1");

        // When
        ProductsPackageDto created = packageService.createPackage(input);
        ProductsPackageDto found = packageService.getPackage(created.getPackageId(), false);

        // Then: packagePrice is calculated from products (1 × 50 USD = 50.0 in USD)
        assertNotNull(created.getPackageId());
        assertEquals(input.getPackageName(), created.getPackageName());
        assertEquals(input.getPackageDescription(), created.getPackageDescription());
        assertEquals(50.0, created.getPackagePrice(), 0.01);
        assertNotNull(created.getProducts());
        assertFalse(created.getProducts().isEmpty());
        assertEquals(created.getPackageId(), found.getPackageId());
        assertEquals(created.getPackageName(), found.getPackageName());
    }

    @Test
    @Transactional
    void givenVoidedPackage_whenGetPackages_thenExcludesVoidedByDefaultAndIncludesWhenRequested() {
        // Given
        ProductsPackageDto created = packageService.createPackage(newPackageDto("Integration Pkg 2"));
        packageService.deletePackage(created.getPackageId());

        // When
        var listExcludingVoided = packageService.getPackages(false);
        var listIncludingVoided = packageService.getPackages(true);

        // Then
        assertTrue(listExcludingVoided.stream()
                .noneMatch(p -> p.getPackageId().equals(created.getPackageId())));
        assertTrue(listIncludingVoided.stream()
                .anyMatch(p -> p.getPackageId().equals(created.getPackageId())));
    }

    @Test
    @Transactional
    void givenExistingPackage_whenUpdatePackage_thenReturnsUpdatedAndGetReflectsChanges() {
        // Given: create package with prod-1; update keeps prod-1 and adds prod-2 (existing products cannot be removed)
        ProductsPackageDto created = packageService.createPackage(newPackageDto("Integration Pkg 3"));
        ProductsPackageDto update = newPackageDto("Updated Name");
        update.setProducts(Set.of(
                ProductDto.builder()
                        .productId("prod-1")
                        .productName("Product One")
                        .productDescription("Desc")
                        .usdPrice(50.0)
                        .build(),
                ProductDto.builder()
                        .productId("prod-2")
                        .productName("Product Two")
                        .productDescription("Updated desc")
                        .usdPrice(75.0)
                        .build()));

        // When
        ProductsPackageDto updated = packageService.updatePackage(created.getPackageId(), update);
        ProductsPackageDto found = packageService.getPackage(created.getPackageId(), false);

        // Then
        assertEquals("Updated Name", updated.getPackageName());
        assertEquals(2, updated.getProducts().size());
        assertTrue(updated.getProducts().stream().anyMatch(p -> "prod-1".equals(p.getProductId())));
        assertTrue(updated.getProducts().stream().anyMatch(p -> "prod-2".equals(p.getProductId())));
        assertEquals("Updated Name", found.getPackageName());
    }

    @Test
    void givenInvalidUuid_whenGetPackage_thenThrowsResourceNotFoundException() {
        // Given
        String invalidUuid = "not-a-uuid";

        // When / Then
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.getPackage(invalidUuid, false));
    }

    @Test
    void givenNonexistentId_whenGetPackage_thenThrowsResourceNotFoundException() {
        // Given
        String nonexistentId = UUID.randomUUID().toString();

        // When / Then
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.getPackage(nonexistentId, false));
    }

    @Test
    void givenPackageDtoWithEmptyProducts_whenCreatePackage_thenThrowsIllegalArgumentException() {
        ProductsPackageDto dto = newPackageDto("No Products");
        dto.setProducts(Set.of());

        assertThrows(IllegalArgumentException.class, () -> packageService.createPackage(dto));
    }

    @Test
    @Transactional
    void givenExistingPackage_whenDeletePackage_thenExcludedFromGetPackageUnlessIncludeVoidedTrue() {
        // Given
        ProductsPackageDto created = packageService.createPackage(newPackageDto("Integration Pkg Delete"));

        // When
        packageService.deletePackage(created.getPackageId());

        // Then
        String packageId = created.getPackageId();
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.getPackage(packageId, false));
        ProductsPackageDto voided = packageService.getPackage(created.getPackageId(), true);
        assertNotNull(voided);
        assertEquals(created.getPackageId(), voided.getPackageId());
    }
}
