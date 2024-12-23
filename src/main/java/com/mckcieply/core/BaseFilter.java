package com.mckcieply.core;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Abstract base class providing common filter criteria for filtering entities in queries.
 *<p>
 * This class defines several standard filtering fields to support entity filtering based on
 * metadata such as creation details, update details, and name. The filtering fields include:
 *
 * <ul>
 *     <li>{@code createdBy}: Filters entities based on the user who created them.</li>
 *     <li>{@code updatedBy}: Filters entities based on the user who last updated them.</li>
 *     <li>{@code name}: Filters entities based on their name using a case-insensitive match.</li>
 *     <li>{@code fromCreatedAt} and {@code toCreatedAt}: Filter entities created within a specific date range.</li>
 *     <li>{@code fromUpdatedAt} and {@code toUpdatedAt}: Filter entities updated within a specific date range.</li>
 * </ul>
 * The dates are parsed using the ISO 8601 format: {@code yyyy-MM-dd'T'HH:mm:ss}.
 * <p>
 * Example: {@code 2024-04-01T12:30:00}
 * <p>
 * Concrete subclasses should extend this class and can override the {@code applyEntitySpecificFilters}
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
     * Adds common filters for creator, updater, name, and date ranges to the query predicates.
     *
     * @param predicates the list of predicates to which filters are added
     * @param root       the root entity used to access attributes (e.g., {@code createdBy}, {@code updatedAt})
     * @param cb         the {@link CriteriaBuilder} for building predicates
     * @throws IllegalArgumentException if any date range is invalid (from date is after to date)
     */
    protected void addBaseFilters(List<Predicate> predicates, Root<?> root, CriteriaBuilder cb) {
        if (getCreatedBy() != null) addStringPredicate(predicates, getCreatedBy(), root.get("createdBy"), cb);
        if (getUpdatedBy() != null) addStringPredicate(predicates, getUpdatedBy(), root.get("updatedBy"), cb);
        if (getName() != null) addStringPredicate(predicates, getName(), root.get("name"), cb);

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


    /**
     * Validates that a date range is valid.
     *
     * This method checks if the `from` date is after the `to` date and throws an
     * {@link IllegalArgumentException} if the range is invalid.
     *
     * @param from the start date of the range
     * @param to   the end date of the range
     * @throws IllegalArgumentException if the `from` date is after the `to` date
     */
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
