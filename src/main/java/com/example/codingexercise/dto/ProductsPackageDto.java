package com.example.codingexercise.dto;

import com.example.codingexercise.config.audit.AuditTrail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object representing a package and its calculated pricing.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductsPackageDto extends AuditTrail {

    private String packageId;

    @NotBlank(message = "Package name is required")
    @Size(max = 255)
    private String packageName;

    @Size(max = 2000)
    private String packageDescription;

    @Size(max = 3, message = "Currency code must be at most 3 characters")
    private String priceCurrency;

    @NotNull(message = "At least one product is required")
    @Size(min = 1, message = "At least one product is required")
    @Valid
    @Builder.Default
    private Set<ProductDto> products = new HashSet<>();

    @DecimalMin(value = "0", message = "Package price must be zero or positive")
    private double packagePrice;

    private double exchangeRate;

}
