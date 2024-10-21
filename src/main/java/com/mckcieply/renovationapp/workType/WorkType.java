package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a work type entity with details for renovation.
 * Inherits common attributes from BaseEntity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "work-types")
public class WorkType extends BaseEntity {


}
