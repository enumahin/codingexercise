package com.example.codingexercise.service.impl;

import com.example.codingexercise.dto.gateway.ProductDto;
import com.example.codingexercise.service.ProductService;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of {@link ProductService} that delegates to a {@link RestTemplate}.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    /**
     * Creates a new gateway using the provided {@link RestTemplate} and product service URL.
     *
     * @param restTemplate HTTP client used to call the product service
     * @param productServiceUrl base URL for the product service API
     */
    public ProductServiceImpl(
            RestTemplate restTemplate,
            @Value("${app.product-service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductDto getProduct(String id) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", basicAuthHeader());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                productServiceUrl + "/{id}",
                HttpMethod.GET,
                entity,
                ProductDto.class,
                id);
        return response.getBody();
    }

    /**
     * Returns the HTTP Basic Authorization header value for test requests.
     * Use with {@code .header("Authorization", basicAuthHeader())} on the request.
     *
     * @return the Basic auth header value
     */
    protected static String basicAuthHeader() {
        String credentials = "user:pass";
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductDto> getProducts() {
        try {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authorization", basicAuthHeader());
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
                    productServiceUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {});
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
