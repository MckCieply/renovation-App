package com.mckcieply.core;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base class for all entities in the system that provides common fields such as id,
 * creation and modification timestamps, and the user who created or last modified the entity.
 *
 * <p>This class is designed to be extended by other entity classes and leverages JPA
 * and Spring Data auditing to automatically handle these fields.</p>
 *
 * <p>The class uses {@link AuditingEntityListener} to populate fields like {@code createdAt},
 * {@code updatedAt}, {@code createdBy}, and {@code updatedBy} automatically based on
 * the system's auditing configuration.</p>
 *
 * <p>Annotations like {@link MappedSuperclass} ensure that the fields are inherited
 * by subclasses, but {@code BaseEntity} itself is not mapped to a table.</p>
 */
@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public abstract class BaseEntity {

    /**
     * The unique identifier for the entity, generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A user-defined name for the entity.
     */
    //    @NotBlank(message = "Name is mandatory")
    //    @Size(min=3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;

    /**
     * The timestamp when the entity was created.
     * This field is automatically populated and cannot be updated.
     */
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the entity was last updated.
     * This field is automatically populated when the entity is modified.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The username of the user who created the entity.
     * This field is automatically populated and cannot be updated.
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    /**
     * The username of the user who last modified the entity.
     * This field is automatically populated when the entity is modified.
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}
