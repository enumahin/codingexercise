package com.example.codingexercise.service;

import com.example.codingexercise.dto.ExchangeRateDto;

/**
 * Service boundary for working with exchange rates.
 */
public interface ExchangeRateService {

    /**
     * Retrieves the exchange rate for USD from the exchange rate service.
     *
     * @return the exchange rate for USD
     */
    ExchangeRateDto getUsdExchangeRate();

    /**
     * Retrieves the exchange rate for a given currency from the exchange rate service.
     *
     * @param currency the currency to get the exchange rate for
     * @return the exchange rate for the given currency
     */
    double getExchangeRate(String currency);

    /**
     * Calculates the price of a package in a given currency.
     *
     * @param quantity the quantity of the package
     * @param usdPrice the price of the package in USD
     * @param currency the currency to calculate the price in
     * @return the price of the package in the given currency
     */
    double calculatePackagePrice(int quantity, double usdPrice, String currency);

    /**
     * Calculates the price of a package in a given currency.
     *
     * @param usdPrice the price of the package in USD
     * @param currency the currency to calculate the price in
     * @return the price of the package in the given currency
     */
    double calculatePackagePrice(double usdPrice, String currency);
}
