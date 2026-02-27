package com.example.codingexercise.service;

import com.example.codingexercise.dto.PackageDto;
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
    PackageDto getPackage(String packageId);

    /**
     * Returns all existing packages.
     *
     * @return list of packages
     */
    List<PackageDto> getPackages();

    /**
     * Creates a new package from the provided data.
     *
     * @param packageDto package details to persist
     * @return the created package
     */
    PackageDto createPackage(PackageDto packageDto);

    /**
     * Updates an existing package.
     *
     * @param id         identifier of the package to update
     * @param packageDto updated package details
     * @return the updated package
     */
    PackageDto updatePackage(String id, PackageDto packageDto);

    /**
     * Deletes the package with the given identifier.
     *
     * @param id identifier of the package to delete
     */
    void deletePackage(String id);
}
