package com.example.codingexercise.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Simple {@link UserDetails} implementation used to integrate with Spring Security.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User implements UserDetails {

    private String userId;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> roles;

    /**
     * Returns the authorities granted to the user.
     *
     * @return collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Returns the user's password.
     *
     * @return encoded password
     */
    @Override
    public String getPassword() {
        return "";
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return "";
    }
}
