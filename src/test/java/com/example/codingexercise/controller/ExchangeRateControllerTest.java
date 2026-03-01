package com.example.codingexercise.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.codingexercise.dto.ExchangeRateDto;
import com.example.codingexercise.service.ExchangeRateService;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for {@link ExchangeRateController} using MockMvc.
 */
@WebMvcTest(ExchangeRateController.class)
@WithMockUser(username = "user", password = "pass", roles = "USER")
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExchangeRateService exchangeRateService;

    @Test
    void givenServiceReturnsExchangeRate_whenGetUsdExchangeRate_thenReturnsOkAndDto() throws Exception {
        // Given
        ExchangeRateDto dto = new ExchangeRateDto(
                1.0,
                "USD",
                LocalDate.of(2025, 2, 27),
                Map.of("EUR", 0.92, "GBP", 0.79));
        when(exchangeRateService.getUsdExchangeRate()).thenReturn(dto);

        // When
        mockMvc.perform(get("/exchange-rates/usd").accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1.0))
                .andExpect(jsonPath("$.base").value("USD"))
                .andExpect(jsonPath("$.date").value("2025-02-27"))
                .andExpect(jsonPath("$.rates.EUR").value(0.92))
                .andExpect(jsonPath("$.rates.GBP").value(0.79));
    }
}
