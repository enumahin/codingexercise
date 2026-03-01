package com.example.codingexercise.integration;

import com.example.codingexercise.service.ExchangeRateService;
import com.example.codingexercise.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@AutoConfigureMockMvc
@Testcontainers
@WithMockUser(username = "user", password = "pass", roles = "USER")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractionContainerBaseTest {

    @Autowired
    protected ExchangeRateService exchangeRateService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RestTemplate restTemplate;

    @Value("${app.product-service.url}")
    protected String productServiceUrl;

    @Value("${app.exchange-rate.url}")
    protected String exchangeRateServiceUrl;


}