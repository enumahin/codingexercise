package com.example.codingexercise.repository;

import java.util.UUID;
import com.example.codingexercise.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, UUID> {

}
