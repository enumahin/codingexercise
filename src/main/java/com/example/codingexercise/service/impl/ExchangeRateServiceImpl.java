package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.ExchangeRateDto;
import com.example.codingexercise.exception.ResourceNotFoundException;
import com.example.codingexercise.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link ExchangeRateService} interface.
 */
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final RestTemplate restTemplate;
    private final String exchangeRateServiceUrl;

    /**
     * Constructs a new {@link ExchangeRateServiceImpl} with the given {@link RestTemplate}
     * and exchange rate service URL from configuration.
     *
     * @param restTemplate the {@link RestTemplate} to use
     * @param exchangeRateServiceUrl the base URL for the exchange rate API
     */
    public ExchangeRateServiceImpl(
            RestTemplate restTemplate,
            @Value("${app.exchange-rate.url}") String exchangeRateServiceUrl) {
        this.restTemplate = restTemplate;
        this.exchangeRateServiceUrl = exchangeRateServiceUrl;
    }

    /**
     * Retrieves the exchange rate for USD from the exchange rate service.
     *
     * @return the exchange rate for USD
     */
    @Override
    public ExchangeRateDto getUsdExchangeRate() {
        return restTemplate.getForObject(exchangeRateServiceUrl, ExchangeRateDto.class);
    }

    /**
     * Retrieves the exchange rate for a given currency from the exchange rate service.
     *
     * @param currency the currency to get the exchange rate for
     * @return the exchange rate for the given currency
     */
    @Override
    public double getExchangeRate(String currency) {
        try {
            ExchangeRateDto exchangeRate = getUsdExchangeRate();
            if (exchangeRate == null) {
                throw new ResourceNotFoundException("No exchange rate found");
            }
            String targetCurrency = (currency != null && !currency.isBlank()) ? currency : exchangeRate.getBase();
            if (targetCurrency.equals(exchangeRate.getBase())) {
                return 1.0;
            }
            if (!exchangeRate.getRates().containsKey(targetCurrency)) {
                throw new ResourceNotFoundException("No exchange rate found for currency: " + targetCurrency);
            }
            return exchangeRate.getRates().get(targetCurrency);
        } catch (Exception e) {
            throw new ResourceNotFoundException("No exchange rate found for currency: " + currency, e);
        }
    }

    /**
     * Calculates the price of a package in a given currency.
     *
     * @param usdPrice the price of the package in USD
     * @param currency the currency to calculate the price in
     * @return the price of the package in the given currency
     */
    @Override
    public double calculatePackagePrice(double usdPrice, String currency) {
        return calculatePackagePrice(1, usdPrice, currency);
    }

    /**
     * Calculates the price of a package in a given currency.
     *
     * @param quantity the quantity of the package
     * @param usdPrice the price of the package in USD
     * @param currency the currency to calculate the price in
     * @return the price of the package in the given currency
     */
    @Override
    public double calculatePackagePrice(int quantity, double usdPrice, String currency) {
        double exchangeRate = getExchangeRate(currency);
        return quantity * usdPrice * exchangeRate;
    }

}
