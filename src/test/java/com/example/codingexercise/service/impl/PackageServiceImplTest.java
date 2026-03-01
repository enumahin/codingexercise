package com.example.codingexercise.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.exception.ResourceNotFoundException;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.model.ProductsPackage;
import com.example.codingexercise.model.mapper.ProductMapper;
import com.example.codingexercise.model.mapper.ProductsPackageMapper;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.ExchangeRateService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PackageServiceImplTest {

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private ProductsPackageMapper packageMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private PackageServiceImpl packageService;

    private UUID packageUuid;
    private ProductsPackage entity;
    private ProductsPackageDto dto;

    @BeforeEach
    void setUp() {
        packageUuid = UUID.randomUUID();
        entity = ProductsPackage.builder()
                .packageId(packageUuid)
                .packageName("Pkg")
                .packageDescription("Desc")
                .priceCurrency("USD")
                .packagePrice(10.0)
                .exchangeRate(1.0)
                .build();
        entity.setProducts(new HashSet<>());
        ProductDto productDto = ProductDto.builder()
                .productId("p1")
                .productName("P")
                .usdPrice(1.0)
                .build();
        dto = ProductsPackageDto.builder()
                .packageId(packageUuid.toString())
                .packageName("Pkg")
                .packageDescription("Desc")
                .priceCurrency("USD")
                .packagePrice(10.0)
                .exchangeRate(1.0)
                .products(Set.of(productDto))
                .build();
    }

    @Test
    void givenPackageIdAndEntityInRepo_whenGetPackage_thenReturnsDtoWithProducts() {
        // Given
        ProductsPackageDto mappedDto = ProductsPackageDto.builder()
                .packageId(packageUuid.toString())
                .packageName("Pkg")
                .products(new HashSet<>())
                .build();
        when(packageRepository.findByPackageId(packageUuid, false)).thenReturn(Optional.of(entity));
        when(packageMapper.toDto(entity)).thenReturn(mappedDto);

        // When
        ProductsPackageDto result = packageService.getPackage(packageUuid.toString(), false);

        // Then
        assertEquals(packageUuid.toString(), result.getPackageId());
        assertEquals("Pkg", result.getPackageName());
        verify(packageRepository).findByPackageId(packageUuid, false);
    }

    @Test
    void givenPackageIdNotInRepo_whenGetPackage_thenThrowsResourceNotFoundException() {
        // Given
        when(packageRepository.findByPackageId(packageUuid, true)).thenReturn(Optional.empty());

        // When / Then
        String id = packageUuid.toString();
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.getPackage(id, true));
    }

    @Test
    void givenInvalidUuid_whenGetPackage_thenThrowsResourceNotFoundException() {
        // Given
        String invalidId = "not-a-uuid";

        // When / Then
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.getPackage(invalidId, false));
    }

    @Test
    void givenRepoReturnsEntities_whenGetPackages_thenReturnsMappedList() {
        // Given
        ProductsPackageDto mappedDto = ProductsPackageDto.builder()
                .packageId(packageUuid.toString())
                .packageName("Pkg")
                .products(new HashSet<>())
                .build();
        when(packageRepository.fetchAll(false)).thenReturn(List.of(entity));
        when(packageMapper.toDto(entity)).thenReturn(mappedDto);

        // When
        List<ProductsPackageDto> result = packageService.getPackages(false);

        // Then
        assertEquals(1, result.size());
        assertEquals("Pkg", result.get(0).getPackageName());
        verify(packageRepository).fetchAll(false);
    }

    @Test
    void givenPackageDtoWithProducts_whenCreatePackage_thenSavesAndReturnsDto() {
        // Given
        ProductsPackageDto savedDto = ProductsPackageDto.builder()
                .packageId(packageUuid.toString())
                .packageName("Pkg")
                .products(new HashSet<>())
                .build();
        when(packageMapper.toEntity(dto)).thenReturn(entity);
        when(packageMapper.toDto(any(ProductsPackage.class))).thenReturn(savedDto);
        when(productMapper.toEntity(any(ProductDto.class)))
                .thenReturn(Product.builder().productId("p1").build());
        when(productMapper.toDto(any(Product.class)))
                .thenReturn(ProductDto.builder().productId("p1").build());
        when(packageRepository.save(any(ProductsPackage.class))).thenReturn(entity);
        when(exchangeRateService.getExchangeRate(anyString())).thenReturn(1.0);
        when(exchangeRateService.calculatePackagePrice(anyDouble(), anyString())).thenAnswer(inv -> inv.getArgument(0));

        // When
        ProductsPackageDto result = packageService.createPackage(dto);

        // Then
        assertEquals(packageUuid.toString(), result.getPackageId());
        verify(packageRepository).save(any(ProductsPackage.class));
    }

    @Test
    void givenPackageDtoWithNullProducts_whenCreatePackage_thenThrowsIllegalArgumentException() {
        // Given
        dto.setProducts(null);

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> packageService.createPackage(dto));
    }

    @Test
    void givenPackageDtoWithEmptyProducts_whenCreatePackage_thenThrowsIllegalArgumentException() {
        // Given
        dto.setProducts(Set.of());

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> packageService.createPackage(dto));
    }

    @Test
    void givenExistingPackageAndUpdateDto_whenUpdatePackage_thenUpdatesAndReturnsDto() {
        // Given
        ProductsPackageDto updatedDto = ProductsPackageDto.builder()
                .packageId(packageUuid.toString())
                .packageName("Updated")
                .products(new HashSet<>())
                .build();
        when(packageRepository.findById(packageUuid)).thenReturn(Optional.of(entity));
        when(packageRepository.save(any(ProductsPackage.class))).thenReturn(entity);
        when(packageMapper.toDto(entity)).thenReturn(updatedDto);
        when(productMapper.toEntity(any(ProductDto.class)))
                .thenReturn(Product.builder().productId("p1").build());
        when(productMapper.toDto(any(Product.class)))
                .thenReturn(ProductDto.builder().productId("p1").build());
        when(exchangeRateService.getExchangeRate(anyString())).thenReturn(1.0);
        when(exchangeRateService.calculatePackagePrice(anyDouble(), anyString()))
                .thenAnswer(inv -> inv.getArgument(0));
        ProductsPackageDto updateDto = ProductsPackageDto.builder()
                .packageName("Updated")
                .priceCurrency("USD")
                .products(Set.of(ProductDto.builder().productId("p1").build()))
                .build();

        // When
        ProductsPackageDto result = packageService.updatePackage(packageUuid.toString(), updateDto);

        // Then
        assertEquals("Updated", result.getPackageName());
        verify(packageRepository).save(entity);
    }

    @Test
    void givenPackageIdNotInRepo_whenUpdatePackage_thenThrowsResourceNotFoundException() {
        // Given
        when(packageRepository.findById(packageUuid)).thenReturn(Optional.empty());

        // When / Then
        String id = packageUuid.toString();
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.updatePackage(id, dto));
    }

    @Test
    void givenUpdateDtoWithEmptyProducts_whenUpdatePackage_thenThrowsIllegalArgumentException() {
        // Given
        dto.setProducts(Set.of());

        // When / Then
        String id = packageUuid.toString();
        assertThrows(IllegalArgumentException.class,
                () -> packageService.updatePackage(id, dto));
    }

    @Test
    void givenDuplicateProductIds_whenCreatePackage_thenThrowsIllegalArgumentException() {
        // Given: same productId twice (validation runs before mapper/repository are used)
        ProductDto p1 = ProductDto.builder().productId("p1").productName("P1").usdPrice(1.0).build();
        ProductsPackageDto createDto = ProductsPackageDto.builder()
                .packageName("Pkg")
                .priceCurrency("USD")
                .products(Set.of(p1, ProductDto.builder().productId("p1").productName("P1").usdPrice(2.0).build()))
                .build();

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> packageService.createPackage(createDto));
    }

    @Test
    void givenUpdateRemovesExistingProduct_whenUpdatePackage_thenThrowsIllegalArgumentException() {
        // Given: existing package has products p1 and p2; update only sends p1
        Product existingP1 = Product.builder().productId("p1").build();
        Product existingP2 = Product.builder().productId("p2").build();
        entity.setProducts(new HashSet<>(Set.of(existingP1, existingP2)));
        when(packageRepository.findById(packageUuid)).thenReturn(Optional.of(entity));
        ProductsPackageDto updateDto = ProductsPackageDto.builder()
                .packageName("Updated")
                .priceCurrency("USD")
                .products(Set.of(ProductDto.builder().productId("p1").productName("P1").usdPrice(1.0).build()))
                .build();

        // When / Then
        assertThrows(IllegalArgumentException.class,
                () -> packageService.updatePackage(packageUuid.toString(), updateDto));
    }

    @Test
    void givenExistingPackage_whenDeletePackage_thenMarksVoidAndSaves() {
        // Given
        when(packageRepository.findById(packageUuid)).thenReturn(Optional.of(entity));
        when(packageRepository.save(any(ProductsPackage.class))).thenReturn(entity);

        // When
        packageService.deletePackage(packageUuid.toString());

        // Then
        verify(packageRepository).findById(packageUuid);
        verify(packageRepository).save(entity);
    }

    @Test
    void givenPackageIdNotInRepo_whenDeletePackage_thenThrowsResourceNotFoundException() {
        // Given
        when(packageRepository.findById(packageUuid)).thenReturn(Optional.empty());

        // When / Then
        String id = packageUuid.toString();
        assertThrows(ResourceNotFoundException.class,
                () -> packageService.deletePackage(id));
    }
}
