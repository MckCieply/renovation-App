package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.core.BaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class BaseControllerTests<T, S extends BaseService<T, Long>> {

    protected abstract S service();

    protected abstract BaseController<T, Long> controller();

    protected abstract List<T> createDummyEntities();

    protected abstract T createDummyEntity();

    @Test
    public void testGetAll() {
        // Arrange
        List<T> entities = createDummyEntities();
        when(service().getAll()).thenReturn(entities);

        // Act
        ResponseEntity<List<T>> response = controller().getAll();

        // Assert
        assertEquals(entities, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Arrange for empty list
        when(service().getAll()).thenReturn(List.of());

        // Act
        response = controller().getAll();

        // Assert
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetAll_NoEntities() {
        // Arrange
        when(service().getAll()).thenReturn(List.of());

        // Act
        ResponseEntity<List<T>> response = controller().getAll();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testAdd() {
        // Arrange
        T entity = createDummyEntity();
        when(service().add(entity)).thenReturn(entity);

        // Act
        ResponseEntity<T> response = controller().add(entity);

        // Assert
        assertEquals(entity, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testAdd_NullEntity() {
        assertThrows(IllegalArgumentException.class, () -> controller().add(null));
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<?> response = controller().delete(id);

        // Assert
        verify(service(), times(1)).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete_NullId() {
        assertThrows(IllegalArgumentException.class, () -> controller().delete(null));
    }

    @Test
    public void testUpdate() {
        // Arrange
        T entity = createDummyEntity();
        when(controller().update(entity)).thenReturn(new ResponseEntity<>(entity, HttpStatus.OK));

        // Act
        ResponseEntity<T> response = controller().update(entity);

        // Assert
        assertEquals(entity, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdate_NullEntity() {
        assertThrows(IllegalArgumentException.class, () -> controller().update(null));
    }


}
