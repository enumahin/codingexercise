package com.example.codingexercise.service;

import com.example.codingexercise.dto.ProductsPackageDto;
import java.util.List;

/**
 * Service boundary for working with package aggregates.
 */
public interface PackageService {

    /**
     * Retrieves a single package by its identifier.
     *
     * @param packageId unique package identifier
     * @return the matching package
     */
    ProductsPackageDto getPackage(String packageId, boolean includeVoided);

    /**
     * Returns all existing packages.
     *
     * @return list of packages
     */
    List<ProductsPackageDto> getPackages(boolean includeVoided);

    /**
     * Creates a new package from the provided data.
     *
     * @param packageDto package details to persist
     * @return the created package
     */
    ProductsPackageDto createPackage(ProductsPackageDto packageDto);

    /**
     * Updates an existing package.
     *
     * @param id         identifier of the package to update
     * @param packageDto updated package details
     * @return the updated package
     */
    ProductsPackageDto updatePackage(String id, ProductsPackageDto packageDto);

    /**
     * Deletes the package with the given identifier.
     *
     * @param id identifier of the package to delete
     */
    void deletePackage(String id);
}
