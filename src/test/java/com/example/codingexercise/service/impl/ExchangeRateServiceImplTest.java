package com.example.codingexercise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.codingexercise.dto.ExchangeRateDto;
import com.example.codingexercise.exception.ResourceNotFoundException;
import com.example.codingexercise.fixture.ExchangeRateFixtures;
import com.example.codingexercise.fixture.GatewayProductFixtures;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

/**
 * Unit tests for {@link ExchangeRateServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

    private static final String EXCHANGE_RATE_SERVICE_URL =
            "https://api.frankfurter.dev/v1/latest?base=USD";

    @Mock
    private RestTemplate restTemplate;

    private ExchangeRateServiceImpl exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateService = new ExchangeRateServiceImpl(restTemplate, EXCHANGE_RATE_SERVICE_URL);
    }

    @Test
    void givenRestTemplateReturnsDto_whenGetUsdExchangeRate_thenReturnsDto() {
        // Given
        ExchangeRateDto expected = new ExchangeRateDto(
                1.0,
                "USD",
                LocalDate.of(2025, 2, 27),
                Map.of("EUR", 0.92, "GBP", 0.79));
        when(restTemplate.getForObject(eq(EXCHANGE_RATE_SERVICE_URL), eq(ExchangeRateDto.class)))
                .thenReturn(expected);

        // When
        ExchangeRateDto result = exchangeRateService.getUsdExchangeRate();

        // Then
        assertNotNull(result);
        assertEquals("USD", result.getBase());
        assertEquals(LocalDate.of(2025, 2, 27), result.getDate());
        assertEquals(0.92, result.getRates().get("EUR"));
        assertEquals(0.79, result.getRates().get("GBP"));
        verify(restTemplate).getForObject(EXCHANGE_RATE_SERVICE_URL, ExchangeRateDto.class);
    }

    @Test
    void givenFixtureRates_whenGetExchangeRate_thenReturnsRateForCurrency() {
        // Given
        when(restTemplate.getForObject(eq(EXCHANGE_RATE_SERVICE_URL), eq(ExchangeRateDto.class)))
                .thenReturn(ExchangeRateFixtures.USD_EXCHANGE_RATES);

        // When
        double eurRate = exchangeRateService.getExchangeRate("EUR");
        double gbpRate = exchangeRateService.getExchangeRate("GBP");

        // Then
        assertEquals(ExchangeRateFixtures.USD_EXCHANGE_RATES.getRates().get("EUR"), eurRate, 0.0001);
        assertEquals(ExchangeRateFixtures.USD_EXCHANGE_RATES.getRates().get("GBP"), gbpRate, 0.0001);
        assertEquals(0.8471, eurRate, 0.0001);
        assertEquals(0.74231, gbpRate, 0.0001);
    }

    @Test
    void givenFixtureRatesAndGatewayProductUsdPrice_whenCalculatePackagePrice_thenReturnsPriceInTargetCurrency() {
        // Given
        when(restTemplate.getForObject(eq(EXCHANGE_RATE_SERVICE_URL), eq(ExchangeRateDto.class)))
                .thenReturn(ExchangeRateFixtures.USD_EXCHANGE_RATES);

        double eurRate = ExchangeRateFixtures.USD_EXCHANGE_RATES.getRates().get("EUR");

        double shieldUsd = GatewayProductFixtures.PRODUCTS.get(0).usdPrice();
        double expectedShieldEur = shieldUsd * eurRate;

        // When
        double result = exchangeRateService.calculatePackagePrice(shieldUsd, "EUR");

        // Then
        assertEquals(expectedShieldEur, result, 0.01);
        assertEquals(1149.0 * 0.8471, result, 0.01);
    }

    @Test
    void givenFixtureRates_whenCalculatePackagePriceWithQuantity_thenReturnsSumInTargetCurrency() {
        // Given
        when(restTemplate.getForObject(eq(EXCHANGE_RATE_SERVICE_URL), eq(ExchangeRateDto.class)))
                .thenReturn(ExchangeRateFixtures.USD_EXCHANGE_RATES);

        double eurRate = ExchangeRateFixtures.USD_EXCHANGE_RATES.getRates().get("EUR");

        // When
        double result = exchangeRateService.calculatePackagePrice(3, 999.0, "EUR");

        // Then
        assertEquals(3 * 999.0 * eurRate, result, 0.01);
    }

    @Test
    void givenFixtureRatesWithUnknownCurrency_whenGetExchangeRate_thenThrowsResourceNotFoundException() {
        // Given
        when(restTemplate.getForObject(eq(EXCHANGE_RATE_SERVICE_URL), eq(ExchangeRateDto.class)))
                .thenReturn(ExchangeRateFixtures.USD_EXCHANGE_RATES);

        // When / Then
        assertThrows(ResourceNotFoundException.class,
                () -> exchangeRateService.getExchangeRate("XXX"));
    }
}
