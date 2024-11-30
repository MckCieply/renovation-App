package com.mckcieply.core;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Abstract base class providing common filter criteria for filtering entities in queries.
 *
 * This class defines several standard filtering fields, such as `daysCreated`, `daysUpdated`,
 * `createdBy`, `updatedBy`, and `name`. It also provides utility methods to add these
 * filters to a query, including predicates for date ranges and case-insensitive string matching.
 *
 * Concrete subclasses should extend this class and can override the `applyEntitySpecificFilters`
 * method to add entity-specific filters to the query.
 */
@Data
abstract public class BaseFilter {
    private String createdBy;
    private String updatedBy;
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromCreatedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toCreatedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromUpdatedAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toUpdatedAt;

    /**
     * Adds common base filters (creation/update date, creator/updater, name) to the provided list of predicates.
     *
     * @param predicates the list of predicates to which the new predicates will be added
     * @param root       the root type in the query, used to access entity attributes
     * @param cb         the {@link CriteriaBuilder} instance for building predicates
     */
    protected void addBaseFilters(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb) {
        if (getCreatedBy() != null) addStringPredicate(predicates, getCreatedBy(), root.get("createdBy"), cb);
        if (getUpdatedBy() != null) addStringPredicate(predicates, getUpdatedBy(), root.get("updatedBy"), cb);
        if (getName() != null) addStringPredicate(predicates, getName(), root.get("name"), cb);

        //Perhaps validation only on from for example
        validateDateRange(getFromCreatedAt(), getToCreatedAt());
        if (getFromCreatedAt() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), getFromCreatedAt()));
        if (getToCreatedAt() != null) predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), getToCreatedAt()));

        validateDateRange(getFromUpdatedAt(), getToUpdatedAt());
        if (getFromUpdatedAt() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("updatedAt"), getFromUpdatedAt()));
        if (getToUpdatedAt() != null) predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), getToUpdatedAt()));

    }

    /**
     * Helper method to add string predicates to the list of predicates.
     *
     * @param predicates     the list of predicates to which the new predicate will be added
     * @param value         the string value to filter
     * @param stringField   the string field to apply the predicate on
     * @param cb            the CriteriaBuilder instance
     */
    protected void addStringPredicate(List<Predicate> predicates, String value, Path<String> stringField, CriteriaBuilder cb) {
        predicates.add(cb.like(cb.lower(stringField), "%" + value.toLowerCase() + "%"));
    }


    protected void validateDateRange(LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null && from.isAfter(to))
            throw new IllegalArgumentException("Invalid date range: from date must be before to date");
    }

    /**
     * Helper method to add additional filters to the query.
     * Override in subclasses to add additional filters.
     */
    public abstract void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates);
}
