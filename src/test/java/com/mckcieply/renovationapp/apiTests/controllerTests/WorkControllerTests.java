package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.work.Work;
import com.mckcieply.renovationapp.work.WorkController;
import com.mckcieply.renovationapp.work.WorkService;
import com.mckcieply.renovationapp.workType.WorkType;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class WorkControllerTests extends BaseControllerTests<Work, WorkService>{

    @Mock
    private WorkService service;

    @Mock
    private WorkController controller;

    Room room = mock(Room.class);
    WorkType workType = mock(WorkType .class);

    @Override
    protected WorkService service() {
        return service;
    }

    @Override
    protected BaseController<Work, Long> controller() {
        return controller;
    }

    @Override
    protected List<Work> createDummyEntities() {
        List<Work> entities = new ArrayList<>();
        entities.add(Work.builder()
                .id(1L)
                .room(room)
                .workType(workType)
                .description("Painting walls")
                .estMaterialCost(1000)
                .estLaborCost(500)
                .finalMaterialCost(1000)
                .finalLaborCost(500)
                .state(EnumWorkState.IN_PROGRESS)
                .paid(false)
                .build());
        entities.add(Work.builder()
                .id(2L)
                .room(room)
                .workType(workType)
                .description("Installing new floor")
                .estMaterialCost(2000)
                .estLaborCost(1000)
                .finalMaterialCost(2000)
                .finalLaborCost(1000)
                .state(EnumWorkState.FINISHED)
                .paid(false)
                .build());
        entities.add(Work.builder()
                .id(3L)
                .room(room)
                .workType(workType)
                .description("Installing new windows")
                .estMaterialCost(3000)
                .estLaborCost(1500)
                .finalMaterialCost(3000)
                .finalLaborCost(1500)
                .state(EnumWorkState.PLANNED)
                .paid(false)
                .build());
        return entities;
    }

    @Override
    protected Work createDummyEntity() {
        return Work.builder()
                .id(1L)
                .room(room)
                .workType(workType)
                .description("Painting walls")
                .estMaterialCost(1000)
                .estLaborCost(500)
                .finalMaterialCost(1000)
                .finalLaborCost(500)
                .state(EnumWorkState.IN_PROGRESS)
                .paid(false)
                .build();
    }
}
