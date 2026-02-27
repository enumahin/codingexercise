package com.example.codingexercise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * JPA Auditing Configuration.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
@Configuration
@EnableJpaAuditing(
    auditorAwareRef = "auditAwareImpl",
    dateTimeProviderRef = "dateTimeProvider",
    modifyOnCreate = true
)
public class JpaAuditingConfiguration {

    /**
     * Returns a DateTimeProvider that provides the current date and time.
     *
     * @return a DateTimeProvider that provides the current date and time
     */
    @Bean
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}

