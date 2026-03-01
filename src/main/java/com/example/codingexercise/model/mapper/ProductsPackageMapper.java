package com.example.codingexercise.model.mapper;

import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.model.ProductsPackage;
import java.util.HashSet;
import org.mapstruct.Mapper;

/**
 * Mapper for converting between ProductsPackage and ProductsPackageDto.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
@edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
        value = "CT_CONSTRUCTOR_THROW",
        justification = "Safe: initialization cannot throw at runtime in our usage"
)
@Mapper(componentModel = "spring")
public interface ProductsPackageMapper {

    /**
     * Convert a Package entity to a PackageDto.
     *
     * @param productsPackage the Package entity
     * @return the PackageDto
     */
    default ProductsPackageDto toDto(ProductsPackage productsPackage) {
        ProductsPackageDto productsPackageDto = ProductsPackageDto.builder()
                .packageId(productsPackage.getPackageId().toString())
                .packageName(productsPackage.getPackageName())
                .packageDescription(productsPackage.getPackageDescription())
                .products(new HashSet<>())
                .priceCurrency(productsPackage.getPriceCurrency())
                .packagePrice(productsPackage.getPackagePrice())
                .exchangeRate(productsPackage.getExchangeRate())
                .build();
        productsPackageDto.setCreatedBy(productsPackage.getCreatedBy());
        productsPackageDto.setLastModifiedBy(productsPackage.getLastModifiedBy());
        productsPackageDto.setVoided(productsPackage.isVoided());
        productsPackageDto.setVoidedBy(productsPackage.getVoidedBy());
        productsPackageDto.setVoidedAt(productsPackage.getVoidedAt());
        productsPackageDto.setVoidReason(productsPackage.getVoidReason());
        productsPackageDto.setUuid(productsPackage.getUuid());
        return productsPackageDto;
    }

    /**
     * Converts a DTO to a Package entity (without products).
     *
     * @param productsPackageDto the DTO
     * @return the entity
     */
    default ProductsPackage toEntity(ProductsPackageDto productsPackageDto) {
        return ProductsPackage.builder()
                .packageName(productsPackageDto.getPackageName())
                .packageDescription(productsPackageDto.getPackageDescription())
                .priceCurrency(productsPackageDto.getPriceCurrency())
                .products(new HashSet<>())
                .packagePrice(productsPackageDto.getPackagePrice())
                .exchangeRate(productsPackageDto.getExchangeRate())
                .build();
    }
} 