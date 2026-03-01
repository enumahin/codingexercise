package com.example.codingexercise.repository;

import com.example.codingexercise.model.ProductsPackage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for accessing and mutating {@link ProductsPackage} entities.
 */
@Repository
public interface PackageRepository extends JpaRepository<ProductsPackage, UUID> {

    /**
     * Returns all packages, optionally including voided ones.
     *
     * @param includeVoided when true, voided packages are included
     * @return list of packages
     */
    @Query("SELECT p FROM ProductsPackage p WHERE (:includeVoided = true OR p.voided = false)")
    List<ProductsPackage> fetchAll(boolean includeVoided);

    /**
     * Finds a package by its id, optionally including voided.
     *
     * @param packageId    the package UUID
     * @param includeVoided when true, voided packages are included
     * @return optional package
     */
    @Query("SELECT p FROM ProductsPackage p WHERE p.packageId = :packageId "
            + "AND (:includeVoided = true OR p.voided = false)")
    Optional<ProductsPackage> findByPackageId(UUID packageId, boolean includeVoided);
}
