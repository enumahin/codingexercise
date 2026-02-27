package com.example.codingexercise.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * JPA entity representing a purchasable package of one or more products.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID packageId;

    @Column(unique = true, nullable = false)
    private String packageName;

    @Column
    private String packageDescription;

    @Column
    private String priceCurrency;

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Product> products;

    @Column
    private double packagePrice;

    @Column
    private double exchangeRate;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Package aPackage)) return false;
        return Double.compare(packagePrice, aPackage.packagePrice) == 0
                && Double.compare(exchangeRate, aPackage.exchangeRate) == 0
                && Objects.equals(packageId, aPackage.packageId) && Objects.equals(packageName, aPackage.packageName)
                && Objects.equals(packageDescription, aPackage.packageDescription)
                && Objects.equals(priceCurrency, aPackage.priceCurrency) && Objects.equals(products, aPackage.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, packageName, packageDescription, priceCurrency, products, packagePrice,
                exchangeRate);
    }
}
