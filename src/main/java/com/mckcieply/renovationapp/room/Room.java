package com.mckcieply.renovationapp.room;


import com.mckcieply.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a room entity with details for renovation.
 * Inherits common attributes from BaseEntity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "rooms")
public class Room extends BaseEntity {

    private Long budgetPlanned;
}
