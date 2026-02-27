package com.example.codingexercise.config.audit;

import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

/**
 * Entity listener for voidable entities.
 *
 * @author Ikenumah (enumahinm@gmail.com)
 */
public class VoidableEntityListener {

    /**
     * Pre-update callback method for voidable entities.
     *
     * @param entity the entity to update
     */
    @PreUpdate
    public void preUpdate(Object entity) {
        if (entity instanceof Voidable voidable && voidable.isVoided() && voidable.getVoidedAt() == null) {
            String principalId = CurrentUser.getCurrentUserId();
            voidable.setVoidedAt(LocalDateTime.now());
            voidable.setVoidedBy(principalId);
        }

    }
}
