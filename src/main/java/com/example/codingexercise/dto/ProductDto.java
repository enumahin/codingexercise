package com.example.codingexercise.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ProductDto {

    private String id;

    @NotBlank(message = "Product ID is required")
    private String productId;

    private ProductsPackageDto productPackage;

    @Size(max = 255)
    @NotBlank(message = "Product name is required")
    private String productName;

    @Size(max = 1000)
    private String productDescription;

    @DecimalMin(value = "1", message = "USD price must be greater than 0")
    private double usdPrice;

    @DecimalMin(value = "0", message = "Local price must be zero or positive")
    private double localPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductDto otherProduct)) {
            return false;
        }
        return Double.compare(usdPrice, otherProduct.usdPrice) == 0
                && Objects.equals(productId, otherProduct.productId)
                && Objects.equals(productName, otherProduct.productName)
                && Objects.equals(productDescription, otherProduct.productDescription)
                && Double.compare(localPrice, otherProduct.localPrice) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productDescription, usdPrice, localPrice);
    }
}
