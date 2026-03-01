package com.example.codingexercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a product that can be included in a package.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @JoinColumn(name = "package_id")
    @ManyToOne
    private ProductsPackage productPackage;

    @Column(nullable = false)
    private String productName;

    @Column
    private String productDescription;

    @Column(nullable = false)
    private double usdPrice;

    @Column(nullable = false)
    private double localPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product otherProduct)) {
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
