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
 * WorkFilter is a filter class extending {@link BaseFilter} that provides additional filtering
 * criteria specific to `Work` entities. This class enables filtering based on work state,
 * payment status, room ID, work type ID, and description.
 *
 * It overrides {@link #applyEntitySpecificFilters(CriteriaBuilder, Root, List)} to add these
 * specific filters to the base query predicates.
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
     * Applies entity-specific filters to the provided list of predicates.
     *
     * <p>This method adds filtering conditions for `state`, `paid`, `roomId`, `workTypeId`,
     * and `description` if their respective fields are not null. For string fields,
     * a case-insensitive match is used.</p>
     *
     * @param cb         the {@link CriteriaBuilder} instance for building predicates
     * @param root       the root type in the query, used to access entity attributes
     * @param predicates the list of predicates to which the new predicates will be added
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
