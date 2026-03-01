package com.example.codingexercise.controller;

import com.example.codingexercise.dto.gateway.ProductDto;
import com.example.codingexercise.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing product operations from the external product service.
 */
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Operations for creating, reading, updating and deleting products")
public class ProductController {

    private final ProductService productService;

    /**
     * Creates the controller with the given product service.
     *
     * @param productService service used to fetch products
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products from the external product service.
     *
     * @return list of products
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    /**
     * Returns a single product by id.
     *
     * @param id product identifier
     * @return the product
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
}
