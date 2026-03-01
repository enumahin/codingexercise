package com.example.codingexercise.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.service.PackageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(PackageController.class)
@WithMockUser(username = "user", password = "pass", roles = "USER")
class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PackageService packageService;

    @Test
    void givenServiceReturnsPackages_whenGetPackages_thenReturnsOkAndList() throws Exception {
        // Given
        ProductsPackageDto pkg = ProductsPackageDto.builder()
                .packageId("id-1")
                .packageName("Package")
                .packagePrice(99.0)
                .build();
        when(packageService.getPackages(false)).thenReturn(List.of(pkg));

        // When
        ResultActions resultActions = mockMvc.perform(get("/packages")
                .param("voided", "false").accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].packageId").value("id-1"))
                .andExpect(jsonPath("$[0].packageName").value("Package"))
                .andExpect(jsonPath("$[0].packagePrice").value(99.0));
    }

    @Test
    void givenVoidedParamTrue_whenGetPackages_thenCallsServiceWithTrue() throws Exception {
        // Given
        when(packageService.getPackages(true)).thenReturn(List.of());

        // When
        mockMvc.perform(get("/packages").param("voided", "true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Then
        verify(packageService).getPackages(true);
    }

    @Test
    void givenPackageIdAndServiceReturnsPackage_whenGetPackage_thenReturnsOkAndPackage() throws Exception {
        // Given
        ProductsPackageDto pkg = ProductsPackageDto.builder()
                .packageId("pkg-1")
                .packageName("My Package")
                .packageDescription("Desc")
                .packagePrice(50.0)
                .build();
        when(packageService.getPackage("pkg-1", false)).thenReturn(pkg);

        // When
        ResultActions resultActions = mockMvc.perform(get("/packages/pkg-1")
                .param("voided", "false").accept(MediaType.APPLICATION_JSON));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId").value("pkg-1"))
                .andExpect(jsonPath("$.packageName").value("My Package"))
                .andExpect(jsonPath("$.packagePrice").value(50.0));
    }

    @Test
    void givenVoidedParamTrue_whenGetPackage_thenCallsServiceWithIncludeVoided() throws Exception {
        // Given
        when(packageService.getPackage("id", true)).thenReturn(ProductsPackageDto.builder().packageId("id").build());

        // When
        mockMvc.perform(get("/packages/id").param("voided", "true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Then
        verify(packageService).getPackage("id", true);
    }

    @Test
    void givenValidRequest_whenCreatePackage_thenReturnsOkAndCreatedPackage() throws Exception {
        // Given
        ProductsPackageDto request = ProductsPackageDto.builder()
                .packageName("New")
                .packageDescription("D")
                .packagePrice(10.0)
                .products(Set.of(ProductDto.builder().productId("p1").productName("P").usdPrice(10.0).build()))
                .build();
        ProductsPackageDto created = ProductsPackageDto.builder()
                .packageId("new-id")
                .packageName("New")
                .packageDescription("D")
                .packagePrice(10.0)
                .build();
        when(packageService.createPackage(any(ProductsPackageDto.class))).thenReturn(created);

        // When
        ResultActions resultActions = mockMvc.perform(post("/packages")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId").value("new-id"))
                .andExpect(jsonPath("$.packageName").value("New"));
        verify(packageService).createPackage(any(ProductsPackageDto.class));
    }

    @Test
    void givenValidRequest_whenUpdatePackage_thenReturnsOkAndUpdatedPackage() throws Exception {
        // Given
        ProductsPackageDto request = ProductsPackageDto.builder()
                .packageName("Updated")
                .packagePrice(25.0)
                .products(Set.of(ProductDto.builder().productId("p1").productName("P").usdPrice(25.0).build()))
                .build();
        ProductsPackageDto updated = ProductsPackageDto.builder()
                .packageId("existing")
                .packageName("Updated")
                .packagePrice(25.0)
                .build();
        when(packageService.updatePackage(eq("existing"), any(ProductsPackageDto.class))).thenReturn(updated);

        // When
        ResultActions resultActions = mockMvc.perform(put("/packages/existing")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));
        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageName").value("Updated"))
                .andExpect(jsonPath("$.packagePrice").value(25.0));
        verify(packageService).updatePackage(eq("existing"), any(ProductsPackageDto.class));
    }

    @Test
    void givenPackageId_whenDeletePackage_thenReturnsNoContent() throws Exception {
        // Given
        String packageId = "to-delete";

        // When
        mockMvc.perform(delete("/packages/" + packageId).with(csrf()))
                .andExpect(status().isNoContent());
        // Then
        verify(packageService).deletePackage(packageId);
    }
}
