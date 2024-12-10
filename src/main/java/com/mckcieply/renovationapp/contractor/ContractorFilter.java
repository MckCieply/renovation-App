package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.util.List;

/**
 * Filter class for querying Contractor entities with specific criteria.
 * <p>
 * This class extends {@link BaseFilter} and adds filtering capabilities for full name, email, and phone number.
 */
@Data
public class ContractorFilter extends BaseFilter {
    private String fullName;
    private String email;
    private String phone;

    /**
     * Applies entity-specific filters to the query predicates.
     *
     * <p>This method adds filtering conditions for `fullName`, `email`, and `phone` if their respective fields are not null.
     * For string fields, a case-insensitive match is used.</p>
     */
    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {
        if (getFullName() != null) addStringPredicate(predicates, getFullName(), root.get("fullName"), cb);
        if (getEmail() != null) addStringPredicate(predicates, getEmail(), root.get("email"), cb);
        if (getPhone() != null) addStringPredicate(predicates, getPhone(), root.get("phone"), cb);
    }
}
