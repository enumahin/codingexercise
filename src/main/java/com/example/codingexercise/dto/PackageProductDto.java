package com.example.codingexercise.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO representing a product that can be included in a package.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PackageProductDto {

    private String id;

    private String productId;

    private PackageDto productPackage;

    private String productName;

    private String productDescription;

    private double usdPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PackageProductDto otherPackageProduct)) {
            return false;
        }
        return Double.compare(usdPrice, otherPackageProduct.usdPrice) == 0
                && Objects.equals(productId, otherPackageProduct.productId)
                && Objects.equals(productName, otherPackageProduct.productName)
                && Objects.equals(productDescription, otherPackageProduct.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productDescription, usdPrice);
    }
}
