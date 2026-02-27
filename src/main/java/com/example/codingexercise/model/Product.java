package com.example.codingexercise.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String productId;

    @JoinColumn
    @ManyToOne
    private Package packageId;

    @Column(nullable = false)
    private String productName;

    @Column
    private String productDescription;

    @Column(nullable = false)
    private double usdPrice;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return Double.compare(usdPrice, product.usdPrice) == 0 && Objects.equals(productId, product.productId)
                && Objects.equals(productName, product.productName)
                && Objects.equals(productDescription, product.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productDescription, usdPrice);
    }
}
