package com.example.codingexercise.model;

import com.example.codingexercise.config.audit.AuditTrail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA entity representing a purchasable package of one or more products.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "packages")
public class ProductsPackage extends AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID packageId;

    @Column(unique = true, nullable = false)
    private String packageName;

    @Column
    private String packageDescription;

    @Column
    private String priceCurrency;

    @OneToMany(mappedBy = "productPackage", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @Column
    private double packagePrice;

    @Column
    private double exchangeRate;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductsPackage otherProductsPackage)) {
            return false;
        }
        return Double.compare(packagePrice, otherProductsPackage.packagePrice) == 0
                && Double.compare(exchangeRate, otherProductsPackage.exchangeRate) == 0
                && Objects.equals(packageId, otherProductsPackage.packageId)
                && Objects.equals(packageName, otherProductsPackage.packageName)
                && Objects.equals(packageDescription, otherProductsPackage.packageDescription)
                && Objects.equals(priceCurrency, otherProductsPackage.priceCurrency)
                && Objects.equals(products, otherProductsPackage.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packageId, packageName, packageDescription, priceCurrency, products, packagePrice,
                exchangeRate);
    }
}
