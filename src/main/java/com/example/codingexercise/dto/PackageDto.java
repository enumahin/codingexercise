package com.example.codingexercise.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data transfer object representing a package and its calculated pricing.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PackageDto {

    private String packageId;

    private String packageName;

    private String packageDescription;

    private String priceCurrency;

    private Set<PackageProductDto> products;

    private double packagePrice;

    private double exchangeRate;

}
