package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;

import java.util.List;

@Data
public class ContractorFilter extends BaseFilter {
    private String fullName;
    private String email;
    private String phone;
    @Override
    public void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates) {
        if (getFullName() != null) addStringPredicate(predicates, getFullName(), root.get("fullName"), cb);
        if (getEmail() != null) addStringPredicate(predicates, getEmail(), root.get("email"), cb);
        if (getPhone() != null) addStringPredicate(predicates, getPhone(), root.get("phone"), cb);
    }
}
