package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.util.List;

@Data
public class RoomFilter extends BaseFilter {
    private Long maxBudgetPlanned;
    private Long minBudgetPlanned;
    

    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {
        if (getMaxBudgetPlanned() != null) predicates.add(cb.lessThanOrEqualTo(root.get("budgetPlanned"), getMaxBudgetPlanned()));
        if (getMinBudgetPlanned() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("budgetPlanned"), getMinBudgetPlanned()));
    }

}
