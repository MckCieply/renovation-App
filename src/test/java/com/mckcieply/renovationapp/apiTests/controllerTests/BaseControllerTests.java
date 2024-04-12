package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.core.BaseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(controller().getAll()).thenReturn(new ResponseEntity<>(entities, HttpStatus.OK));
        //doReturn(new ResponseEntity<>(entities, HttpStatus.OK)).when(controller()).getAll();

        // Act
        ResponseEntity<List<T>> response = controller().getAll();

        // Assert
        assertEquals(entities, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
