package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.util.List;

@Data
public class RoomFilter extends BaseFilter {
    private Long budgetPlanned;

    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {
        if (getBudgetPlanned() != null) predicates.add(cb.equal(root.get("budgetPlanned"), getBudgetPlanned()));
    }

}
