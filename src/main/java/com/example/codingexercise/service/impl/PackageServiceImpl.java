package com.example.codingexercise.service.impl;

import static java.lang.String.format;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.dto.ProductsPackageDto;
import com.example.codingexercise.exception.ResourceNotFoundException;
import com.example.codingexercise.model.Product;
import com.example.codingexercise.model.ProductsPackage;
import com.example.codingexercise.model.mapper.ProductMapper;
import com.example.codingexercise.model.mapper.ProductsPackageMapper;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.ExchangeRateService;
import com.example.codingexercise.service.PackageService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Default {@link PackageService} implementation that persists packages via {@link PackageRepository}.
 */
@Slf4j
@AllArgsConstructor
@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final ProductsPackageMapper packageMapper;
    private final ProductMapper productMapper;
    private final ExchangeRateService exchangeRateService;
    
    private static final String PACKAGE_NOT_FOUND = "Package with Id: %s not found.";

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsPackageDto getPackage(String packageId, boolean includeVoided) {
        UUID uuid = parsePackageId(packageId);
        return packageRepository.findByPackageId(uuid, includeVoided)
                .map(this::toDtoWithProducts)
                .orElseThrow(() -> {
                    String message = format(PACKAGE_NOT_FOUND, packageId);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductsPackageDto> getPackages(boolean includeVoided) {
        return packageRepository.fetchAll(includeVoided)
                .stream()
                .map(this::toDtoWithProducts)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsPackageDto createPackage(ProductsPackageDto packageDto) {
        
        if (packageDto.getProducts() == null || packageDto.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Products are required for a package");
        }
        validateNoDuplicateProducts(packageDto.getProducts());

        ProductsPackage productsPackage = packageMapper.toEntity(packageDto);
        if (productsPackage.getPriceCurrency() == null || productsPackage.getPriceCurrency().isBlank()) {
            productsPackage.setPriceCurrency("USD");
        }

        for (var productDto : packageDto.getProducts()) {
            Product product = productMapper.toEntity(productDto);
            product.setProductPackage(productsPackage);
            productsPackage.getProducts().add(product);
        }
        calculatePackagePrice(productsPackage);
        ProductsPackage savedPackage = packageRepository.save(productsPackage);

        ProductsPackageDto savedProductPackageDto = packageMapper.toDto(savedPackage);
        savedProductPackageDto.setProducts(new HashSet<>());
        savedPackage.getProducts().forEach(product ->
                savedProductPackageDto.getProducts().add(productMapper.toDto(product)));
        return savedProductPackageDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductsPackageDto updatePackage(String packageId, ProductsPackageDto packageDto) {
        if (packageDto.getProducts() == null || packageDto.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Products are required for a package");
        }
        validateNoDuplicateProducts(packageDto.getProducts());
        UUID uuid = parsePackageId(packageId);
        return packageRepository.findById(uuid)
                .map(productsPackage -> {
                    Set<String> existingProductIds = productsPackage.getProducts().stream()
                            .map(Product::getProductId)
                            .collect(Collectors.toSet());
                    Set<String> newProductIds = packageDto.getProducts().stream()
                            .map(ProductDto::getProductId)
                            .filter(id -> id != null && !id.isBlank())
                            .collect(Collectors.toSet());
                    if (!newProductIds.containsAll(existingProductIds)) {
                        throw new IllegalArgumentException(
                                "Existing products cannot be removed from a package. You may only add new products.");
                    }
                    productsPackage.setPackageName(packageDto.getPackageName());
                    productsPackage.setPackageDescription(packageDto.getPackageDescription());
                    String currency = packageDto.getPriceCurrency();
                    productsPackage.setPriceCurrency(
                            currency != null && !currency.isBlank() ? currency : "USD");
                    productsPackage.setPackagePrice(packageDto.getPackagePrice());
                    productsPackage.setExchangeRate(packageDto.getExchangeRate());
                    productsPackage.getProducts().clear();
                    for (var productDto : packageDto.getProducts()) {
                        Product product = productMapper.toEntity(productDto);
                        product.setProductPackage(productsPackage);
                        productsPackage.getProducts().add(product);
                    }
                    calculatePackagePrice(productsPackage);
                    ProductsPackage saved = packageRepository.save(productsPackage);
                    return toDtoWithProducts(saved);
                })
                .orElseThrow(() -> new ResourceNotFoundException(packageId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePackage(String packageId) {
        UUID uuid = parsePackageId(packageId);
        packageRepository.findById(uuid)
                .map(productPackage -> {
                    try {
                        productPackage.markAsVoid("");
                        return packageRepository.save(productPackage);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Failed to delete package: " + ex.getMessage(), ex);
                    }
                })
                .orElseThrow(() -> {
                    String message = format(PACKAGE_NOT_FOUND, packageId);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
    }

    private UUID parsePackageId(String packageId) {
        try {
            return UUID.fromString(packageId);
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid package ID: " + packageId, e);
        }
    }

    private ProductsPackageDto toDtoWithProducts(ProductsPackage productsPackage) {
        ProductsPackageDto dto = packageMapper.toDto(productsPackage);
        productsPackage.getProducts().forEach(product ->
             dto.getProducts().add(productMapper.toDto(product)));
        return dto;
    }

    private void validateNoDuplicateProducts(Collection<ProductDto> products) {
        if (products == null) {
            return;
        }
        List<String> ids = products.stream()
                .map(ProductDto::getProductId)
                .filter(id -> id != null && !id.isBlank())
                .toList();
        Set<String> unique = new HashSet<>(ids);
        if (unique.size() < ids.size()) {
            throw new IllegalArgumentException("A package cannot contain the same product more than once. "
                    + "Remove duplicate products.");
        }
    }

    private void calculatePackagePrice(ProductsPackage productsPackage) {
        double packagePrice = 0;
        double exchangeRate = exchangeRateService.getExchangeRate(productsPackage.getPriceCurrency());
        for (var product : productsPackage.getProducts()) {
            double localPrice = exchangeRateService.calculatePackagePrice(
                    product.getUsdPrice(), productsPackage.getPriceCurrency());
            product.setLocalPrice(localPrice);
            packagePrice += product.getLocalPrice();
        }
        productsPackage.setPackagePrice(packagePrice);
        productsPackage.setExchangeRate(exchangeRate);
    }
}
