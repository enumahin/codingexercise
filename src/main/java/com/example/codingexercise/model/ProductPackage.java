package com.example.codingexercise.model;

import java.util.List;

/**
 * Simple in-memory representation of a package used by the legacy tests.
 */
public class ProductPackage {
    private String id;
    private String name;
    private String description;
    private List<String> productIds;

    /**
     * Creates a new {@code ProductPackage} instance.
     *
     * @param id          unique identifier of the package
     * @param name        display name of the package
     * @param description human readable description of the package
     * @param productIds  identifiers of the products contained in the package
     */
    public ProductPackage(String id, String name, String description, List<String> productIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productIds = productIds;
    }

    /**
     * Returns the package identifier.
     *
     * @return package id
     */
    public String getId() {
        return id;
    }

    /**
     * Updates the package identifier.
     *
     * @param id new package id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the package name.
     *
     * @return package name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the package name.
     *
     * @param name new package name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the package description.
     *
     * @return description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the package description.
     *
     * @param description new description text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the identifiers of products included in the package.
     *
     * @return list of product ids
     */
    public List<String> getProductIds() {
        return productIds;
    }

    /**
     * Updates the identifiers of products included in the package.
     *
     * @param productIds new list of product ids
     */
    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }
}
