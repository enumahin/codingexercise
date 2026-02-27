package com.example.codingexercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a product that can be included in a package.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "products")
public class PackageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @JoinColumn(name = "package_id")
    @ManyToOne
    private Package productPackage;

    @Column(nullable = false)
    private String productName;

    @Column
    private String productDescription;

    @Column(nullable = false)
    private double usdPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PackageProduct otherPackageProduct)) {
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
