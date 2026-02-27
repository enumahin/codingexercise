package com.example.codingexercise.config;

import com.example.codingexercise.config.audit.CurrentUser;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * This class is an implementation of Spring Data's {@link AuditorAware} which
 * provides the current auditor to the JPA Auditing framework.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
@Log4j2
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {


    /**
     * Returns the current auditor. This is the value of the {@code createdBy},
     * {@code lastModifiedBy} fields in the {@link AuditTrail} class.
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            // Assuming UserDetails implementation has a method to get the user ID
            String userId = CurrentUser.getCurrentUserId();
            return Optional.of(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
