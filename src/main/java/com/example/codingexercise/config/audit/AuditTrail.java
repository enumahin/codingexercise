package com.example.codingexercise.config.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * Abstract base class for any entity that requires audit trail information.
 * Contains the following information:
 * <ul>
 *     <li>Created by</li>
 *     <li>Created at</li>
 *     <li>Last modified by</li>
 *     <li>Last modified at</li>
 *     <li>Voided</li>
 *     <li>Voided by</li>
 *     <li>Voided at</li>
 *     <li>Void reason</li>
 * </ul>
 */
@SQLRestriction("voided <> false")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, VoidableEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrail implements Voidable {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    private boolean voided = false;

    @Column(name = "voided_by")
    private String voidedBy;

    @Column(name = "voided_at")
    private LocalDateTime voidedAt;

    @Column(name = "void_reason")
    private String voidReason;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Override
    public void markAsVoid(String reason) {
        this.voided = true;
        this.voidReason = reason;
    }

    /**
     * Generate a UUID if one is not already set.
     */
    @PrePersist
    public void generateUuid() {
        if (uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
