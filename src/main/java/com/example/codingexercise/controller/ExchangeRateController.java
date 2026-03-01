package com.example.codingexercise.controller;

import com.example.codingexercise.dto.ExchangeRateDto;
import com.example.codingexercise.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing exchange rate operations from the external exchange rate service.
 */
@RestController
@RequestMapping("/exchange-rates")
@Tag(name = "Exchange Rates", description = "Operations for retrieving exchange rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    /**
     * Creates the controller with the given exchange rate service.
     *
     * @param exchangeRateService service used to fetch exchange rates
     */
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    /**
     * Returns the current USD exchange rates from the external service.
     *
     * @return the exchange rate data with base USD and rates map
     */
    @GetMapping("/usd")
    @Operation(summary = "Get USD exchange rate", 
        description = "Returns the current USD exchange rate from the external service.", tags = "Exchange Rates")
    @ApiResponse(responseCode = "200", description = "Exchange rate data with base USD and rates map")
    public ResponseEntity<ExchangeRateDto> getUsdExchangeRate() {
        return ResponseEntity.ok(exchangeRateService.getUsdExchangeRate());
    }

    /**
     * Returns the current USD exchange rates from the external service.
     *
     * @return the exchange rate data with base USD and rates map
     */
    @GetMapping("/{currency}")
    @Operation(summary = "Get USD exchange rate",
        description = "Returns the current USD exchange rate from the external service.", tags = "Exchange Rates")
    @ApiResponse(responseCode = "200", description = "Exchange rate data with base USD and rates map")
    public ResponseEntity<Double> getUsdExchangeRate(@PathVariable String currency) {
        return ResponseEntity.ok(exchangeRateService.getExchangeRate(currency));
    }
}
