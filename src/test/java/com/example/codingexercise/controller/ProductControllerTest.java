package com.example.codingexercise.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.codingexercise.dto.gateway.ProductDto;
import com.example.codingexercise.service.ProductService;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ProductController.class)
@WithMockUser(username = "user", password = "pass", roles = "USER")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void givenServiceReturnsProducts_whenGetProducts_thenReturnsOkAndList() throws Exception {
        // Given
        List<ProductDto> products = List.of(
                new ProductDto("id1", "Product 1", 10.0, 10.0),
                new ProductDto("id2", "Product 2", 20.0, 20.0));
        when(productService.getProducts()).thenReturn(products);

        // When
        mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("id1"))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[0].usdPrice").value(10.0))
                .andExpect(jsonPath("$[1].id").value("id2"));
    }

    @Test
    void givenServiceReturnsEmptyList_whenGetProducts_thenReturnsOkAndEmptyArray() throws Exception {
        // Given
        when(productService.getProducts()).thenReturn(List.of());

        // When
        mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void givenProductIdAndServiceReturnsProduct_whenGetProduct_thenReturnsOkAndProduct() throws Exception {
        // Given
        ProductDto dto = new ProductDto("p1", "Product One", 15.0, 12.0);
        when(productService.getProduct("p1")).thenReturn(dto);

        // When
        mockMvc.perform(get("/products/p1").accept(MediaType.APPLICATION_JSON))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("p1"))
                .andExpect(jsonPath("$.name").value("Product One"))
                .andExpect(jsonPath("$.usdPrice").value(15.0))
                .andExpect(jsonPath("$.localPrice").value(12.0));
    }

    @Test
    void givenPathId_whenGetProduct_thenCallsServiceWithPathId() throws Exception {
        // Given
        when(productService.getProduct("abc-123")).thenReturn(new ProductDto("abc-123", "X", 0.0, 0.0));

        // When
        mockMvc.perform(get("/products/abc-123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Then
    }
}
