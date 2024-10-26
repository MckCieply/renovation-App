package com.mckcieply.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * @param days (Optional) number of days to filter by creation date
     * @param name (Optional) name filter
     * @return a list of filtered entities
     */
    public List<T> getFiltered(Integer days, String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());

        List<Predicate> predicates = new ArrayList<>();

        // Apply creation date filter if `days` is present
       Optional.ofNullable(days).ifPresent(d -> {
            LocalDateTime fromDate = LocalDateTime.now().minusDays(d);
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
        });

        // Apply name filter if `name` is present
        Optional.ofNullable(name).ifPresent(n -> {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + n.toLowerCase() + "%"));
        });

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Helper method to get the class of the entity.
     * Override in subclasses to return the appropriate entity class.
     */
    protected abstract Class<T> getEntityClass();
}
