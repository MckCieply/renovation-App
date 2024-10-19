package com.mckcieply.core;

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
}
