package com.example.codingexercise.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private Set<PackageProduct> packageProducts;

    @Column
    private double packagePrice;

    @Column
    private double exchangeRate;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Package otherPackage)) {
            return false;
        }
        return Double.compare(packagePrice, otherPackage.packagePrice) == 0
                && Double.compare(exchangeRate, otherPackage.exchangeRate) == 0
                && Objects.equals(packageId, otherPackage.packageId)
                && Objects.equals(packageName, otherPackage.packageName)
                && Objects.equals(packageDescription, otherPackage.packageDescription)
                && Objects.equals(priceCurrency, otherPackage.priceCurrency)
                && Objects.equals(packageProducts, otherPackage.packageProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, packageName, packageDescription, priceCurrency, packageProducts, packagePrice,
                exchangeRate);
    }
}
