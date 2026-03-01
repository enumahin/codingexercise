package com.example.codingexercise.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;


class UserTest {

    @Test
    void givenBuilderWithFields_whenBuild_thenFieldsAreSetAndUsernamePasswordReturned() {
        // When
        User user = User.builder()
                .userId("u1")
                .username("john")
                .password("secret")
                .roles(List.of())
                .build();

        // Then
        assertEquals("u1", user.getUserId());
        assertNotNull(user.getRoles());
        assertEquals("john", user.getUsername());
        assertEquals("secret", user.getPassword());
    }

    @Test
    void givenUser_whenGetAuthorities_thenReturnsUserAuthority() {
        // Given
        User user = new User();

        // When
        Collection<? extends GrantedAuthority> auths = user.getAuthorities();

        // Then
        assertNotNull(auths);
        assertEquals(1, auths.size());
        assertEquals("USER", auths.iterator().next().getAuthority());
    }

    @Test
    void givenUserWithPassword_whenGetPassword_thenReturnsPassword() {
        // Given
        User user = User.builder().password("any").build();

        // When / Then
        assertEquals("any", user.getPassword());
    }

    @Test
    void givenUserWithUsername_whenGetUsername_thenReturnsUsername() {
        // Given
        User user = User.builder().username("any").build();

        // When / Then
        assertEquals("any", user.getUsername());
    }

    @Test
    void givenNoArgsConstructor_whenCreate_thenUsernamePasswordNullAndAuthoritiesContainUser() {
        // When
        User user = new User();

        // Then (unset fields return empty string from getters)
        assertEquals("", user.getPassword());
        assertEquals("", user.getUsername());
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().stream().anyMatch(a -> "USER".equals(a.getAuthority())));
    }
}
