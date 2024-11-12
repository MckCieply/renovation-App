package com.mckcieply.core;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;

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
    private Integer daysCreated;
    private Integer daysUpdated;
    private String createdBy;
    private String updatedBy;
    private String name;

    /**
     * Adds common base filters (creation/update date, creator/updater, name) to the provided list of predicates.
     *
     * @param predicates the list of predicates to which the new predicates will be added
     * @param root       the root type in the query, used to access entity attributes
     * @param cb         the {@link CriteriaBuilder} instance for building predicates
     */
    protected void addBaseFilters(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb) {
        if (getDaysCreated() != null) addDatePredicate(predicates, getDaysCreated(), root.get("createdAt"), cb);
        if (getDaysUpdated() != null) addDatePredicate(predicates, getDaysUpdated(), root.get("updatedAt"), cb);
        if (getCreatedBy() != null) addStringPredicate(predicates, getCreatedBy(), root.get("createdBy"), cb);
        if (getUpdatedBy() != null) addStringPredicate(predicates, getUpdatedBy(), root.get("updatedBy"), cb);
        if (getName() != null) addStringPredicate(predicates, getName(), root.get("name"), cb);

    }

    /**
     * Helper method to add date predicates to the list of predicates.
     *
     * @param predicates     the list of predicates to which the new predicate will be added
     * @param days          the number of days to filter
     * @param dateField     the date field to apply the predicate on
     * @param cb            the CriteriaBuilder instance
     */
    protected void addDatePredicate(List<Predicate> predicates, Integer days, Path<LocalDateTime> dateField, CriteriaBuilder cb) {
        LocalDateTime fromDate = LocalDateTime.now().minusDays(days);
        predicates.add(cb.greaterThanOrEqualTo(dateField, fromDate));
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

    /**
     * Helper method to add additional filters to the query.
     * Override in subclasses to add additional filters.
     */
    public abstract void applyEntitySpecificFilters(CriteriaBuilder cb, Root<?> root, List<Predicate> predicates);
}
