package com.example.codingexercise.controller;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.dto.PackageProductDto;
import com.example.codingexercise.integration.AbstractionContainerBaseTest;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for {@link PackageController} exercising the HTTP endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackageControllerIntegrationTests extends AbstractionContainerBaseTest{

    @Test
    void givenAPackage_whenCreatingAPackage_thenThePackageIsCreated() throws Exception {
      // Given a package
      PackageDto packageDto = PackageDto.builder()
        .packageName("Test Name")
        .packageDescription("Test Desc")
        .products(Set.of(
                PackageProductDto.builder()
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

      // When creating the package  
      ResultActions result = mockMvc.perform(post("/packages")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(packageDto)));

      // Then
        Set<String> productIds = packageDto.getProducts().stream()
                .map(PackageProductDto::getProductId)
                .collect(Collectors.toSet());
      result.andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(packageDto.getPackageName()))
        .andExpect(jsonPath("$.description").value(packageDto.getPackageDescription()))
        .andExpect(jsonPath("$.productIds").value(productIds))
        .andExpect(jsonPath("$.packagePrice").value(packageDto.getPackagePrice()))
        .andExpect(jsonPath("$.exchangeRate").value(packageDto.getExchangeRate()))
        .andExpect(jsonPath("$.priceCurrency").value(packageDto.getPriceCurrency()));

    }

    @Test
    void givenPackagesAreCreated_whenGetPackages_thenReturnALlCreatedPackages() throws Exception {

       PackageDto packageDto1 = PackageDto.builder()
        .packageName("Test Name")
        .packageDescription("Test Desc")
        .products(Set.of(
                PackageProductDto.builder()
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


       PackageDto packageDto2 = PackageDto.builder()
       .packageName("Test Name 2")
       .packageDescription("Test Desc")
       .products(Set.of(
               PackageProductDto.builder()
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
        .andExpect(jsonPath("$[0].products").value(packageDto1.getProducts().stream().map(PackageProductDto::getProductId).collect(Collectors.toSet())))
        .andExpect(jsonPath("$[0].packagePrice").value(packageDto1.getPackagePrice()))
        .andExpect(jsonPath("$[0].exchangeRate").value(packageDto1.getExchangeRate()))
        .andExpect(jsonPath("$[0].priceCurrency").value(packageDto1.getPriceCurrency()))
        .andExpect(jsonPath("$[1].packageName").value(packageDto2.getPackageName()))
        .andExpect(jsonPath("$[1].packageDescription").value(packageDto2.getPackageDescription()))
        .andExpect(jsonPath("$[1].products").value(packageDto2.getProducts().stream().map(PackageProductDto::getProductId).collect(Collectors.toSet())))
        .andExpect(jsonPath("$[1].packagePrice").value(packageDto2.getPackagePrice()))
        .andExpect(jsonPath("$[1].exchangeRate").value(packageDto2.getExchangeRate()))
        .andExpect(jsonPath("$[1].priceCurrency").value(packageDto2.getPriceCurrency()));
    }

    @Test
    void givenPackageId_whenGetPackage_thenReturnThePackage() throws Exception {
       // Given
       PackageDto packageDto = PackageDto.builder()
       .packageName("Test Name")
       .packageDescription("Test Desc")
       .products(Set.of(
               PackageProductDto.builder()
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
        PackageDto savedPackage = objectMapper.readValue(savedPackageJson, PackageDto.class);

        // When
        ResultActions result = mockMvc.perform(get("/packages/{id}", savedPackage.getPackageId()));

        // Then
        result.andExpect(status().isOk())
        .andExpect(jsonPath("$.packageName").value(savedPackage.getPackageName()))
        .andExpect(jsonPath("$.packageDescription").value(savedPackage.getPackageDescription()))
        .andExpect(jsonPath("$.products").value(savedPackage.getProducts().stream()
        .map(PackageProductDto::getProductId).collect(Collectors.toSet())))
        .andExpect(jsonPath("$.packagePrice").value(savedPackage.getPackagePrice()))
        .andExpect(jsonPath("$.exchangeRate").value(savedPackage.getExchangeRate()))
        .andExpect(jsonPath("$.priceCurrency").value(savedPackage.getPriceCurrency()));
    }

    @Test
    void givenPackageId_whenUpdatePackage_thenReturnTheUpdatedPackage() throws Exception {
       // Given
       PackageDto packageDto = PackageDto.builder()
       .packageName("Test Name")
       .packageDescription("Test Desc")
       .products(Set.of(
               PackageProductDto.builder()
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
        PackageDto savedPackage = objectMapper.readValue(savedPackageJson, PackageDto.class);
        
        // When
        PackageDto updatedPackageDto = PackageDto.builder()
        .packageName("Test Name Updated")
        .packageDescription("Test Desc Updated")
        .products(Set.of(
                PackageProductDto.builder()
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
        .andExpect(jsonPath("$.products").value(updatedPackageDto.getProducts().stream()
        .map(PackageProductDto::getProductId).collect(Collectors.toSet())))
        .andExpect(jsonPath("$.packagePrice").value(updatedPackageDto.getPackagePrice()))
        .andExpect(jsonPath("$.exchangeRate").value(updatedPackageDto.getExchangeRate()))
        .andExpect(jsonPath("$.priceCurrency").value(updatedPackageDto.getPriceCurrency()));
      }

    @Test
    void givenPackageId_whenDeletePackage_thenReturnTheDeletedPackage() throws Exception{
       // Given
       PackageDto packageDto = PackageDto.builder()
       .packageName("Test Name")
       .packageDescription("Test Desc")
       .products(Set.of(
               PackageProductDto.builder()
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
        PackageDto savedPackage = objectMapper.readValue(savedPackageJson, PackageDto.class);

        // When
        mockMvc.perform(delete("/packages/{id}", savedPackage.getPackageId()));
        ResultActions getDeletedPackageResult = mockMvc.perform(get("/packages/{id}?voided=true", savedPackage.getPackageId()));

        // Then
        getDeletedPackageResult.andExpect(status().isOk())
        .andExpect(jsonPath("$.voided").value(true));
      }
}
