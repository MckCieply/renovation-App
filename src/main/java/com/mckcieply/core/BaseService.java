package com.mckcieply.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base service that provides common CRUD operations for any entity type.
 * This service is designed to work with a {@link BaseRepository} to perform standard operations
 * such as retrieving all entities, adding, deleting, and updating an entity.
 *
 * @param <T>  the type of the entity the service will manage
 * @param <ID> the type of the entity's identifier
 */
public abstract class BaseService<T, ID> {

    private final BaseRepository<T, ID> repository;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Constructs a new BaseService with the provided repository.
     *
     * @param repository the repository used for interacting with the database
     */
    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }


    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities
     */
    public List<T> getAll() {
        return repository.findAll();
    }

    /**
     * Adds a new entity to the repository.
     *
     * @param entity the entity to be added
     * @return the saved entity after being persisted
     */
    public T add(T entity) {
        return repository.save(entity);
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the identifier of the entity to be deleted
     */
    public void delete(ID id) {
        repository.deleteById(id);
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity the entity to be updated
     * @return the updated entity after being persisted
     */
    public T update(T entity) {
        return repository.save(entity);
    }

    /**
     * Retrieves entities with optional filters applied.
     *
     * @param daysCreated (Optional) number of days to filter by creation date
     * @param daysUpdated (Optional) number of days to filter by updated date
     * @param name (Optional) name filter
     * @param createdBy (Optional) user who created the entity
     * @param updatedBy (Optional) user who last updated the entity
     * @return a list of filtered entities
     */
    public List<T> getFiltered(Integer daysCreated, Integer daysUpdated, String name, String createdBy, String updatedBy) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());

        List<Predicate> predicates = new ArrayList<>();

        if(daysCreated != null)
            addDatePredicate(predicates, daysCreated, root.get("createdAt"), cb);

        if(daysUpdated != null)
            addDatePredicate(predicates, daysUpdated, root.get("updatedAt"), cb);

        if (name != null)
            addStringPredicate(predicates, name, root.get("name"), cb);

        if (createdBy != null)
            addStringPredicate(predicates, createdBy, root.get("createdBy"), cb);

        if (updatedBy != null)
            addStringPredicate(predicates, updatedBy, root.get("updatedBy"), cb);


        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Helper method to add date predicates to the list of predicates.
     *
     * @param predicates     the list of predicates to which the new predicate will be added
     * @param days          the number of days to filter
     * @param dateField     the date field to apply the predicate on
     * @param cb            the CriteriaBuilder instance
     */
    private void addDatePredicate(List<Predicate> predicates, Integer days, Path<LocalDateTime> dateField, CriteriaBuilder cb) {
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
    private void addStringPredicate(List<Predicate> predicates, String value, Path<String> stringField, CriteriaBuilder cb) {
            predicates.add(cb.like(cb.lower(stringField), "%" + value.toLowerCase() + "%"));
    }

    /**
     * Helper method to get the class of the entity.
     * Override in subclasses to return the appropriate entity class.
     */
    protected abstract Class<T> getEntityClass();
}
