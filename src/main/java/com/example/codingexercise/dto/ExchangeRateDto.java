package com.example.codingexercise.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing an exchange rate response with base currency and rates.
 */
@Schema(
        name = "ExchangeRateDto",
        description = "DTO representing an exchange rate response with base currency and rates."
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeRateDto {

    @Schema(
            description = "The amount the exchange rate is based on.",
            example = "1.0"
    )
    private double amount = 1.0;

    @Schema(
            description = "The base currency of the exchange rate.",
            example = "USD"
    )
    private String base;

    @Schema(
            description = "The date of the exchange rate.",
            example = "2021-01-01"
    )
    private LocalDate date;

    @Schema(
            description = "The rates of the exchange rate.",
            example = "{\"USD\": 1.0, \"EUR\": 0.85}"
    )
    private Map<String, Double> rates = new HashMap<>();
}
