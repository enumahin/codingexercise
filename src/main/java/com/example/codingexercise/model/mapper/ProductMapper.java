package com.example.codingexercise.model.mapper;

import com.example.codingexercise.dto.ProductDto;
import com.example.codingexercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for converting between Product and ProductDto.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Map a Product to a ProductDto.
     *
     * @param product the Product to map
     * @return the ProductDto
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Map a ProductDto to a Product.
     *
     * @param productDto the ProductDto to map
     * @return the Product
     */
    default Product toEntity(ProductDto productDto) {
        return Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .productDescription(productDto.getProductDescription())
                .usdPrice(productDto.getUsdPrice())
                .localPrice(productDto.getLocalPrice())
                .build();
    }

    /**
     * Map a Product to a ProductDto.
     *
     * @param product the Product to map
     * @return the ProductDto
     */
    default ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .usdPrice(product.getUsdPrice())
                .localPrice(product.getLocalPrice())
                .build();
    }
}
