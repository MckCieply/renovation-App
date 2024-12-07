package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * Filter class for querying Room entities with specific criteria.
 * <p>
 * This class extends {@link BaseFilter} and adds filtering capabilities for budget constraints.
 * It allows filtering based on the planned budget range by specifying minimum and maximum values.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomFilter extends BaseFilter {
    private Long maxBudgetPlanned;
    private Long minBudgetPlanned;


    /**
     * Applies room-specific filters to the query predicates.
     * <p>
     * This method adds filters based on the planned budget range:
     * <ul>
     *     <li>{@code maxBudgetPlanned}: Filters rooms with a planned budget less than or equal to this value.</li>
     *     <li>{@code minBudgetPlanned}: Filters rooms with a planned budget greater than or equal to this value.</li>
     * </ul>
     */
    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {
        if (getMaxBudgetPlanned() != null) predicates.add(cb.lessThanOrEqualTo(root.get("budgetPlanned"), getMaxBudgetPlanned()));
        if (getMinBudgetPlanned() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("budgetPlanned"), getMinBudgetPlanned()));
    }

}
