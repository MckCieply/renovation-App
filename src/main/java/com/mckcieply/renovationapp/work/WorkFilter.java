package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseFilter;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Filter class for querying Work entities with specific criteria.
 * <p>
 * This class extends {@link BaseFilter} and adds filtering capabilities for work state, payment status,
 * room ID, work type ID, and description.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkFilter extends BaseFilter {
    private EnumWorkState state;
    private Boolean paid;
    private Long roomId;
    private Long workTypeId;
    private String description;

    /**
     * Applies entity-specific filters to the query predicates.
     *
     * <p>This method adds filtering conditions for `state`, `paid`, `roomId`, `workTypeId`,
     * and `description` if their respective fields are not null. For string fields,
     * a case-insensitive match is used.</p>
     */
    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {

        if (getState() != null) predicates.add(cb.equal(root.get("state"), getState()));
        if (getPaid() != null) predicates.add(cb.equal(root.get("paid"), getPaid()));
        if (getRoomId() != null) predicates.add(cb.equal(root.get("room").get("id"), getRoomId()));
        if (getWorkTypeId() != null) predicates.add(cb.equal(root.get("workType").get("id"), getWorkTypeId()));
        if (getDescription() != null) addStringPredicate(predicates, getDescription(), root.get("description"), cb);
        
    }
}
