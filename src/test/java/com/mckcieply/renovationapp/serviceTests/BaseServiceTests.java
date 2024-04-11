package com.mckcieply.renovationapp.serviceTests;

import com.mckcieply.core.BaseRepository;
import com.mckcieply.core.BaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTests<T, R extends BaseRepository<T, Long>> {

    protected abstract R repository();

    protected abstract BaseService<T, Long> service();

    protected abstract List<T> createDummyEntities();

    protected abstract T createDummyEntity();

    @Test
    public void testGetAll() {
        // Arrange
        List<T> entities = createDummyEntities();
        when(repository().findAll()).thenReturn(entities);

        // Act
        List<T> result = service().getAll();

        // Assert
        assertEquals(entities, result);
    }

    @Test
    public void testAdd() {
        // Arrange
        T entity = createDummyEntity();

        // Act
        when(repository().save(entity)).thenReturn(entity);
        T result = service().add(entity);

        // Assert
        assertEquals(entity, result);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        service().delete(id);

        // Assert
        verify(repository(), times(1)).deleteById(id);
    }

    @Test
    public void testUpdate() {
        // Arrange
        T updatedEntity = createDummyEntity();

        // Act
        when(repository().save(any())).thenReturn(updatedEntity); // Mock save method to return the updated entity

        T result = service().update(updatedEntity);

        // Assert
        assertEquals(updatedEntity, result);
    }

}
