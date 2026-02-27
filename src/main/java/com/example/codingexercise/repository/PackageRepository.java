package com.example.codingexercise.repository;

import com.example.codingexercise.model.Package;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for accessing and mutating {@link Package} entities.
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, UUID> {

}
