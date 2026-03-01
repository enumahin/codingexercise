package com.example.codingexercise.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ExchangeRateDto}.
 */
class ExchangeRateDtoTest {

    @Test
    void givenAllArgsConstructor_whenCreate_thenAllFieldsAreSet() {
        // Given
        LocalDate date = LocalDate.of(2025, 2, 27);
        Map<String, Double> rates = Map.of("EUR", 0.92, "GBP", 0.79);

        // When
        ExchangeRateDto dto = new ExchangeRateDto(1.0, "USD", date, rates);

        // Then
        assertEquals(1.0, dto.getAmount());
        assertEquals("USD", dto.getBase());
        assertEquals(date, dto.getDate());
        assertEquals(rates, dto.getRates());
        assertEquals(0.92, dto.getRates().get("EUR"));
        assertEquals(0.79, dto.getRates().get("GBP"));
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenRatesIsEmptyMap() {
        // When
        ExchangeRateDto dto = new ExchangeRateDto();

        // Then
        assertEquals(1.0, dto.getAmount());
        assertEquals(null, dto.getBase());
        assertEquals(null, dto.getDate());
        assertNotNull(dto.getRates());
        assertTrue(dto.getRates().isEmpty());
    }

    @Test
    void givenDto_whenSettersCalled_thenFieldsAreUpdated() {
        // Given
        ExchangeRateDto dto = new ExchangeRateDto();
        LocalDate date = LocalDate.of(2025, 1, 15);
        Map<String, Double> rates = Map.of("JPY", 149.5);

        // When
        dto.setAmount(2.0);
        dto.setBase("USD");
        dto.setDate(date);
        dto.setRates(rates);

        // Then
        assertEquals(2.0, dto.getAmount());
        assertEquals("USD", dto.getBase());
        assertEquals(date, dto.getDate());
        assertEquals(rates, dto.getRates());
        assertEquals(149.5, dto.getRates().get("JPY"));
    }
}
