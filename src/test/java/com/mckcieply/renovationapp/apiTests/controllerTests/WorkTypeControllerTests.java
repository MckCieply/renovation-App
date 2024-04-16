package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.workType.WorkType;
import com.mckcieply.renovationapp.workType.WorkTypeController;
import com.mckcieply.renovationapp.workType.WorkTypeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class WorkTypeControllerTests extends BaseControllerTests<WorkType, WorkTypeService>{

    @Mock
    private WorkTypeService service;

    @InjectMocks
    private WorkTypeController controller;

    @Override
    protected WorkTypeService service() {
        return service;
    }

    @Override
    protected BaseController<WorkType, Long> controller() {
        return controller;
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
