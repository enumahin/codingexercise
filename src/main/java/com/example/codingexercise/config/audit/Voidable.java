package com.example.codingexercise.config.audit;

import java.time.LocalDateTime;

/**
 * Interface for entities that can be voided.
 */
public interface Voidable {

    /**
     * Gets the voided status for the entity.
     *
     * @return the voided status for the entity
     */
    boolean isVoided();

    /**
     * Sets the voided status for the entity.
     *
     * @param voided the voided status for the entity
     */
    void setVoided(boolean voided);

    /**
     * Gets the voided at for the entity.
     *
     * @return the date and time the entity was voided
     */
    LocalDateTime getVoidedAt();

    /**
     * Sets the voided at for the entity.
     *
     * @param voidedAt the date and time the entity was voided
     */
    void setVoidedAt(LocalDateTime voidedAt);

    /**
     * Gets the voided by for the entity.
     *
     * @return the user ID of the user who voided the entity
     */
    String getVoidedBy();

    /**
     * Sets the voided by for the entity.
     *
     * @param voidedBy the user ID of the user who voided the entity
     */
    void setVoidedBy(String voidedBy);

    /**
     * Gets the void reason for the entity.
     *
     * @return the void reason for the entity
     */
    String getVoidReason();

    /**
     * Sets the void reason for the entity.
     *
     * @param voidReason the reason for voiding the entity
     */
    void setVoidReason(String voidReason);

    /**
     * Marks the entity as voided.
     *
     * @param voidReason the reason for voiding the entity
     */
    void markAsVoid(String voidReason);
}
