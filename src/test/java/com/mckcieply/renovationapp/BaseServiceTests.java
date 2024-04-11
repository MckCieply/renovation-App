package com.mckcieply.renovationapp;

import com.mckcieply.core.BaseRepository;
import com.mckcieply.core.BaseService;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BaseServiceTests {

    @Mock
    private BaseRepository<DummyEntity, Long> repository;

    @InjectMocks
    private DummyService service;

    @Test
    public void BaseService_getAll(){
        List<DummyEntity> entities = new ArrayList<>();
        entities.add(DummyEntity.builder().id(1L).name("Test1").build());
        entities.add(DummyEntity.builder().id(2L).name("Test2").build());
        entities.add(DummyEntity.builder().id(3L).name("Test3").build());



        when(repository.findAll()).thenReturn(entities);

        List<DummyEntity> result = service.getAll();

        assertEquals(entities, result);

    }

    @Test
    public void BaseService_add(){
        DummyEntity entity = DummyEntity.builder().id(1L).name("Test1").build();

        when(repository.save(entity)).thenReturn(entity);

        DummyEntity result = service.add(entity);

        assertEquals(entity, result);

    }

    @Test
    public void BaseService_delete(){
        Long id = 1L;

        // Act
        service.delete(id);

        // Assert
        verify(repository, times(1)).deleteById(id);

    }

    @Test
    public void BaseService_update(){
        DummyEntity entity = DummyEntity.builder().id(1L).name("Test1").build();
        DummyEntity updatedEntity = DummyEntity.builder().id(1L).name("Test1Updated").build();

        when(repository.save(entity)).thenReturn(entity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);

        service.add(entity);
        DummyEntity result = service.update(updatedEntity);

        assertEquals(updatedEntity, result);
        verify(repository, times(1)).save(entity);
        verify(repository, times(1)).save(updatedEntity);

    }

    @Data
    @Builder
    static class DummyEntity {

        private Long id;

        private String name;

    }

    private static class DummyService extends BaseService<DummyEntity, Long> {
        public DummyService(BaseRepository<DummyEntity, Long> repository) {
            super(repository);
        }
    }
}



