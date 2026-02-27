package com.example.codingexercise.controller;

import com.example.codingexercise.dto.PackageDto;
import com.example.codingexercise.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes CRUD operations for managing packages and their products.
 */
@RestController
@RequestMapping("/packages")
@Tag(name = "Packages", description = "Operations for creating, reading, updating and deleting packages")
public class PackageController {

    private final PackageService packageService;

    /**
     * Controller.
     *
     * @param packageService the package service
     */
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    /**
     * Creates a new package from the provided details.
     *
     * @param packageDto the package to create
     * @return the created package including its generated identifier
     */
    @PostMapping()
    @Operation(summary = "Create a package", description = "Creates a new package with one or more products.")
    @ApiResponse(responseCode = "200",
            description = "Package created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PackageDto.class)))
    public ResponseEntity<PackageDto> createPackage(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the package to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PackageDto.class)))
            PackageDto packageDto) {
        return ResponseEntity.ok(packageService.createPackage(packageDto));
    }

    /**
     * Retrieves a package by its identifier.
     *
     * @param id the package identifier
     * @return the matching package
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a package", description = "Retrieves a single package by its identifier.")
    @ApiResponse(responseCode = "200",
            description = "Package found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PackageDto.class)))
    @ApiResponse(responseCode = "404", description = "Package not found")
    public ResponseEntity<PackageDto> getPackage(
            @Parameter(description = "Unique identifier of the package")
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "false") boolean includeVoided) {
        return ResponseEntity.ok(packageService.getPackage(id, includeVoided));
    }

    /**
     * Lists all available packages.
     *
     * @return a list of all packages
     */
    @GetMapping()
    @Operation(summary = "List packages", description = "Returns all available packages.")
    @ApiResponse(responseCode = "200",
            description = "Packages returned successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PackageDto.class)))
    public ResponseEntity<List<PackageDto>> getPackages(
            @RequestParam(required = false, defaultValue = "false") boolean includeVoided) {
        return ResponseEntity.ok(packageService.getPackages(includeVoided));
    }

    /**
     * Updates an existing package with the provided changes.
     *
     * @param id         the package identifier
     * @param packageDto the updated package data
     * @return the updated package
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a package", description = "Updates an existing package.")
    @ApiResponse(responseCode = "200",
            description = "Package updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PackageDto.class)))
    @ApiResponse(responseCode = "404", description = "Package not found")
    public ResponseEntity<PackageDto> updatePackage(
            @Parameter(description = "Unique identifier of the package to update")
            @PathVariable String id,
            @RequestBody PackageDto packageDto) {
        return ResponseEntity.ok(packageService.updatePackage(id, packageDto));
    }

    /**
     * Deletes an existing package.
     *
     * @param id the identifier of the package to delete
     * @return an empty response with HTTP 204 when deletion succeeds
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a package", description = "Deletes an existing package.")
    @ApiResponse(responseCode = "204", description = "Package deleted successfully")
    @ApiResponse(responseCode = "404", description = "Package not found")
    public ResponseEntity<Void> deletePackage(
            @Parameter(description = "Unique identifier of the package to delete")
            @PathVariable String id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}
