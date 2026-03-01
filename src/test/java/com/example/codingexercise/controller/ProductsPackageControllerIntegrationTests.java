package com.example.codingexercise.controller;

import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.integration.AbstractionContainerBaseTest;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.ExchangeRateService;
import com.example.codingexercise.service.ProductService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductsPackageControllerIntegrationTests extends AbstractionContainerBaseTest {

    static List<com.example.codingexercise.dto.gateway.ProductDto> products;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PackageRepository packageRepository;

    @BeforeEach
    void setUp() {
        packageRepository.deleteAll();
        if (products == null) {
            products = productService.getProducts();
        }
        System.out.println(products);
    }

    @Test
    void givenAPackageDto_whenCreatingAPackage_thenThePackageIsCreated() throws Exception {
        // Given
        String localCurrency = "USD";
        double exchangeRate = exchangeRateService.getExchangeRate(localCurrency);
        com.example.codingexercise.dto.gateway.ProductDto productDto = productService.getProducts().get(0);
        ProductsPackageDto packageDto = ProductsPackageDto.builder()
        .packageName("Test Package 1")
        .packageDescription("Test Desc")
        .products(Set.of(
                ProductDto.builder()
                        .productId(productDto.id())
                        .productName(productDto.name())
                        .usdPrice(productDto.usdPrice())
                        .build()
        ))
        .priceCurrency(localCurrency)
        .packagePrice(productDto.usdPrice() * exchangeRate)
        .exchangeRate(exchangeRate)
        .build();

        // When
        ResultActions result = mockMvc.perform(post("/packages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(packageDto)));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.packageName").value(packageDto.getPackageName()))
                .andExpect(jsonPath("$.packageDescription").value(packageDto.getPackageDescription()))
                .andExpect(jsonPath("$.products").isNotEmpty())
                .andExpect(jsonPath("$.packagePrice").value(packageDto.getPackagePrice()))
                .andExpect(jsonPath("$.exchangeRate").value(packageDto.getExchangeRate()))
                .andExpect(jsonPath("$.priceCurrency").value(packageDto.getPriceCurrency()));
    }

    @Test
    void givenPackagesAreCreated_whenGetPackages_thenReturnALlCreatedPackages() throws Exception {
        // Given
       ProductsPackageDto packageDto1 = ProductsPackageDto.builder()
        .packageName("Test Package 2")
        .packageDescription("Test Desc")
        .products(Set.of(
                ProductDto.builder()
                        .id(UUID.randomUUID().toString())
                        .productId("productId")
                        .productName("Product Name")
                        .productDescription("Product Description")
                        .usdPrice(100)
                        .build()
        ))
        .priceCurrency("USD")
        .packagePrice(100)
        .exchangeRate(1.0)
        .build();


       ProductsPackageDto packageDto2 = ProductsPackageDto.builder()
       .packageName("Test Package 3")
       .packageDescription("Test Desc")
       .products(Set.of(
               ProductDto.builder()
                       .id(UUID.randomUUID().toString())
                       .productId("productId")
                       .productName("Product Name")
                       .productDescription("Product Description")
                       .usdPrice(100)
                       .build()
       ))
       .priceCurrency("USD")
       .packagePrice(100)
       .exchangeRate(1.0)
       .build();

       mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto1)));

        mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto2)));

        // When
        ResultActions result = mockMvc.perform(get("/packages"));

        // Then
        result.andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[0].packageName").value(packageDto1.getPackageName()))
        .andExpect(jsonPath("$[0].packageDescription").value(packageDto1.getPackageDescription()))
        .andExpect(jsonPath("$[0].packagePrice").value(packageDto1.getPackagePrice()))
        .andExpect(jsonPath("$[0].exchangeRate").value(packageDto1.getExchangeRate()))
        .andExpect(jsonPath("$[0].priceCurrency").value(packageDto1.getPriceCurrency()))
        .andExpect(jsonPath("$[1].packageName").value(packageDto2.getPackageName()))
        .andExpect(jsonPath("$[1].packageDescription").value(packageDto2.getPackageDescription()))
        .andExpect(jsonPath("$[1].products.size()").value(packageDto2.getProducts().size()))
        .andExpect(jsonPath("$[1].packagePrice").value(packageDto2.getPackagePrice()))
        .andExpect(jsonPath("$[1].exchangeRate").value(packageDto2.getExchangeRate()))
        .andExpect(jsonPath("$[1].priceCurrency").value(packageDto2.getPriceCurrency()));
    }

    @Test
    void givenPackageId_whenGetPackage_thenReturnThePackage() throws Exception {
       // Given
       ProductsPackageDto packageDto = ProductsPackageDto.builder()
       .packageName("Test Package 4")
       .packageDescription("Test Desc")
       .products(Set.of(
               ProductDto.builder()
                       .id(UUID.randomUUID().toString())
                       .productId("productId")
                       .productName("Product Name")
                       .productDescription("Product Description")
                       .usdPrice(100)
                       .build()
       ))
       .priceCurrency("USD")
       .packagePrice(100)
       .exchangeRate(1.0)
       .build();
       
       ResultActions postResult = mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto)));

        String savedPackageJson = postResult.andReturn().getResponse().getContentAsString();
        ProductsPackageDto savedPackage = objectMapper.readValue(savedPackageJson, ProductsPackageDto.class);

        // When
        ResultActions result = mockMvc.perform(get("/packages/{id}", savedPackage.getPackageId()));

        // Then
        result.andExpect(status().isOk())
        .andExpect(jsonPath("$.packageName").value(savedPackage.getPackageName()))
        .andExpect(jsonPath("$.packageDescription").value(savedPackage.getPackageDescription()))
        .andExpect(jsonPath("$.products.size()").value(1))
        .andExpect(jsonPath("$.packagePrice").value(savedPackage.getPackagePrice()))
        .andExpect(jsonPath("$.exchangeRate").value(savedPackage.getExchangeRate()))
        .andExpect(jsonPath("$.priceCurrency").value(savedPackage.getPriceCurrency()));
    }

    @Test
    void givenPackageId_whenUpdatePackage_thenReturnTheUpdatedPackage() throws Exception {
       // Given
       ProductsPackageDto packageDto = ProductsPackageDto.builder()
       .packageName("Test Package 5")
       .packageDescription("Test Desc")
       .products(Set.of(
               ProductDto.builder()
                       .id(UUID.randomUUID().toString())
                       .productId("productId")
                       .productName("Product Name")
                       .productDescription("Product Description")
                       .usdPrice(100)
                       .build()
       ))
       .priceCurrency("USD")
       .packagePrice(100)
       .exchangeRate(1.0)
       .build();

       ResultActions postResult = mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto)));

        String savedPackageJson = postResult.andReturn().getResponse().getContentAsString();
        ProductsPackageDto savedPackage = objectMapper.readValue(savedPackageJson, ProductsPackageDto.class);

        // When
        ProductsPackageDto updatedPackageDto = ProductsPackageDto.builder()
        .packageName("Test Package Updated")
        .packageDescription("Test Desc Updated")
        .products(Set.of(
                ProductDto.builder()
                        .id(UUID.randomUUID().toString())
                        .productId("productId")
                        .productName("Product Name Updated")
                        .productDescription("Product Description Updated")
                        .usdPrice(100)
                        .build()
        ))
        .priceCurrency("USD")
        .packagePrice(100)
        .exchangeRate(1.0)
        .build();
        
        ResultActions result = mockMvc.perform(put("/packages/{id}", savedPackage.getPackageId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedPackageDto)));

        // Then
        result.andExpect(status().isOk())
        .andExpect(jsonPath("$.packageName").value(updatedPackageDto.getPackageName()))
        .andExpect(jsonPath("$.packageDescription").value(updatedPackageDto.getPackageDescription()))
        .andExpect(jsonPath("$.products.size()").value(1))
        .andExpect(jsonPath("$.packagePrice").value(updatedPackageDto.getPackagePrice()))
        .andExpect(jsonPath("$.exchangeRate").value(updatedPackageDto.getExchangeRate()))
        .andExpect(jsonPath("$.priceCurrency").value(updatedPackageDto.getPriceCurrency()));
      }

    @Transactional
    @Test
    void givenPackageId_whenDeletePackage_thenReturnTheDeletedPackage() throws Exception{
       // Given
       ProductsPackageDto packageDto = ProductsPackageDto.builder()
       .packageName("Test Package 6")
       .packageDescription("Test Desc")
       .products(Set.of(
               ProductDto.builder()
               .id(UUID.randomUUID().toString())
               .productId("productId")
               .productName("Product Name")
               .productDescription("Product Description")
               .usdPrice(100)
               .build()
       ))
       .priceCurrency("USD")
       .packagePrice(100)
       .exchangeRate(1.0)
       .build();

       ResultActions postResult = mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto)));

        String savedPackageJson = postResult.andReturn().getResponse().getContentAsString();
        ProductsPackageDto savedPackage = objectMapper.readValue(savedPackageJson, ProductsPackageDto.class);

        // When
        mockMvc.perform(delete("/packages/{id}", savedPackage.getPackageId()));
        ResultActions getDeletedPackageResult = mockMvc.perform(
                get("/packages/{id}?voided=true", savedPackage.getPackageId()));

        // Then
        getDeletedPackageResult.andExpect(status().isOk())
        .andExpect(jsonPath("$.voided").value(true));
      }

}
