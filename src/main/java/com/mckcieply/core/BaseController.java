package com.mckcieply.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, ID> {

    private final BaseService<T, ID> baseService;

    public BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<T>> getAll() {
        List<T> entities = baseService.getAll();
        if(entities.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<T> add(@RequestBody T entity) {
        baseService.add(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") ID id) {
        baseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<T> update(@RequestBody T entity) {
        baseService.update(entity);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }
}
