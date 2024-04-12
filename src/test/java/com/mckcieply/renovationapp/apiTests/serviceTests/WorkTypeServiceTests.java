package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.workType.WorkType;
import com.mckcieply.renovationapp.workType.WorkTypeRepository;
import com.mckcieply.renovationapp.workType.WorkTypeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class WorkTypeServiceTests extends BaseServiceTests<WorkType, WorkTypeRepository>{

    @Mock
    private WorkTypeRepository repository;

    @InjectMocks
    private WorkTypeService service;

    @Override
    protected WorkTypeRepository repository() {
        return repository;
    }

    @Override
    protected BaseService<WorkType, Long> service() {
        return service;
    }

    @Override
    protected List<WorkType> createDummyEntities() {
        List<WorkType> entities = new ArrayList<>();
        entities.add(WorkType.builder().id(1L).name("Painting").build());
        entities.add(WorkType.builder().id(2L).name("Plumbing").build());
        entities.add(WorkType.builder().id(3L).name("Electricity").build());
        return entities;
    }

    @Override
    protected WorkType createDummyEntity() {
        return WorkType.builder().id(1L).name("Painting").build();
    }
}
