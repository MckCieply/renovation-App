package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.work.Work;
import com.mckcieply.renovationapp.work.WorkRepository;
import com.mckcieply.renovationapp.work.WorkService;
import com.mckcieply.renovationapp.workType.WorkType;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class WorkServiceTests extends BaseServiceTests<Work, WorkRepository>{
    
    @Mock
    private WorkRepository repository;
    
    @InjectMocks
    private WorkService service;

    Room room = mock(Room.class);
    WorkType workType = mock(WorkType .class);
    
    @Override
    protected WorkRepository repository() {
        return repository;
    }

    @Override
    protected BaseService<Work, Long> service() {
        return service;
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
