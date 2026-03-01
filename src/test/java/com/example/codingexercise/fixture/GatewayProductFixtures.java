package com.example.codingexercise.fixture;

import com.example.codingexercise.dto.gateway.ProductDto;
import java.util.List;

/**
 * Shared gateway {@link ProductDto} data for unit and integration tests.
 */
public final class GatewayProductFixtures {

    private GatewayProductFixtures() {}

    /** List of products with id, name, and usdPrice for use in tests. */
    public static final List<ProductDto> PRODUCTS = List.of(
            new ProductDto("VqKb4tyj9V6i", "Shield", 1149.0, 1149.0),
            new ProductDto("DXSQpv6XVeJm", "Helmet", 999.0, 999.0),
            new ProductDto("7dgX6XzU3Wds", "Sword", 899.0, 899.0),
            new ProductDto("PKM5pGAh9yGm", "Axe", 799.0, 799.0),
            new ProductDto("7Hv0hA2nmci7", "Knife", 349.0, 349.0),
            new ProductDto("500R5EHvNlNB", "Gold Coin", 249.0, 249.0),
            new ProductDto("IP3cv7TcZhQn", "Platinum Coin", 399.0, 399.0),
            new ProductDto("IJOHGYkY2CYq", "Bow", 649.0, 649.0),
            new ProductDto("8anPsR2jbfNW", "Silver Coin", 50.0, 50.0));
}
