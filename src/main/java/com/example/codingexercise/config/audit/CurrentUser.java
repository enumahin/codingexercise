package com.example.codingexercise.config.audit;

import static java.lang.String.format;

import com.example.codingexercise.exception.AuthenticationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * Class to get the current user from the application context.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
@Log4j2
public final class CurrentUser {

    private CurrentUser() {}

    /**
     * Get the current user from the application context.
     *
     * @return the current user's ID
     */
    public static String getCurrentUserId() {
        // Get the current authentication from the security context
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                log.info("Authentication is not null and isAuthenticated");

                User user = (User) authentication.getPrincipal();

                // Get userId from database or token if Oauth2 is in use

                return user.getUsername();
            }
            log.info("Authentication is null or not isAuthenticated");
            throw new AuthenticationException("Authentication is null or not isAuthenticated");
        } catch (Exception e) {
            throw new IllegalArgumentException(format("Error passing current user id: %s", e.getMessage()), e);
        }
    }

}
