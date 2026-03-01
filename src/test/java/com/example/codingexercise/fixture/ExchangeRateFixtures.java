package com.example.codingexercise.fixture;

import com.example.codingexercise.dto.ExchangeRateDto;
import java.time.LocalDate;
import java.util.Map;

/**
 * Shared {@link ExchangeRateDto} data for unit and integration tests.
 */
public final class ExchangeRateFixtures {

    private ExchangeRateFixtures() {}

    /** Exchange rate data with USD base and rates for 2026-02-27. */
    public static final ExchangeRateDto USD_EXCHANGE_RATES = new ExchangeRateDto(
            1.0,
            "USD",
            LocalDate.of(2026, 2, 27),
            Map.ofEntries(
                    Map.entry("AUD", 1.4072),
                    Map.entry("BRL", 5.1556),
                    Map.entry("CAD", 1.3671),
                    Map.entry("CHF", 0.7712),
                    Map.entry("CNY", 6.8582),
                    Map.entry("CZK", 20.537),
                    Map.entry("DKK", 6.3294),
                    Map.entry("EUR", 0.8471),
                    Map.entry("GBP", 0.74231),
                    Map.entry("HKD", 7.8237),
                    Map.entry("HUF", 319.04),
                    Map.entry("IDR", 16801.0),
                    Map.entry("ILS", 3.1433),
                    Map.entry("INR", 91.08),
                    Map.entry("ISK", 121.56),
                    Map.entry("JPY", 155.98),
                    Map.entry("KRW", 1441.97),
                    Map.entry("MXN", 17.2122),
                    Map.entry("MYR", 3.891),
                    Map.entry("NOK", 9.4947),
                    Map.entry("NZD", 1.6714),
                    Map.entry("PHP", 57.683),
                    Map.entry("PLN", 3.5784),
                    Map.entry("RON", 4.3166),
                    Map.entry("SEK", 9.0337),
                    Map.entry("SGD", 1.2657),
                    Map.entry("THB", 31.085),
                    Map.entry("TRY", 43.96),
                    Map.entry("ZAR", 15.9405)));
}
