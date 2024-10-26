package com.mckcieply.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A generic base controller class that provides common CRUD operations for any entity type.
 * It can be extended by specific controllers to handle standard operations for their respective entities.
 *
 * @param <T>  the type of the entity the controller will manage
 * @param <ID> the type of the entity's identifier
 */
public abstract class BaseController<T, ID> {

    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    /**
     * Retrieves all entities managed by this controller.
     *
     * @return a {@link ResponseEntity} containing a list of all entities;
     *         returns HTTP 204 (No Content) if no entities are found.
     */
    @GetMapping("/all")
    public ResponseEntity<List<T>> getAll() {
        List<T> entities = baseService.getAll();
        if (entities.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    /**
     * Adds a new entity to the system.
     *
     * @param entity the entity to be added, provided in the request body
     * @return a {@link ResponseEntity} containing the added entity and HTTP status 201 (Created)
     * @throws IllegalArgumentException if the entity is null
     */
    @PostMapping("/add")
    public ResponseEntity<T> add(@RequestBody T entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");

        baseService.add(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the identifier of the entity to be deleted
     * @return a {@link ResponseEntity} with HTTP status 204 (No Content) upon successful deletion
     * @throws IllegalArgumentException if the provided ID is null
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id cannot be null");
        baseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates an existing entity.
     *
     * @param entity the updated entity data, provided in the request body
     * @return a {@link ResponseEntity} containing the updated entity and HTTP status 200 (OK)
     * @throws IllegalArgumentException if the entity is null
     */
    @PutMapping("/update")
    public ResponseEntity<T> update(@RequestBody T entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        baseService.update(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * Retrieves all entities or filtered entities based on optional query parameters.
     *
     * @param daysCreated (Optional) Number of days to filter by creation date
     * @param daysUpdated (Optional) Number of days to filter by updated date
     * @param name (Optional) Name filter
     * @return a ResponseEntity containing the filtered list of entities
     */
    @GetMapping("/filtered")
    public ResponseEntity<List<T>> getFiltered(
            @RequestParam(required = false) Integer daysCreated,
            @RequestParam(required = false) Integer daysUpdated,
            @RequestParam(required = false) String name
            ) {
        List<T> entities = baseService.getFiltered(daysCreated, daysUpdated, name);
        if (entities.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
}
