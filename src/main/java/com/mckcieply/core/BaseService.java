package com.mckcieply.core;

import java.util.List;

public abstract class BaseService<T, ID> {

    private final BaseRepository<T, ID> repository;

    public BaseService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }



    public List<T> getAll() {
        return repository.findAll();
    }

    public T add(T entity) {
        return repository.save(entity);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public T update(T entity) {
        return repository.save(entity);
    }
}
