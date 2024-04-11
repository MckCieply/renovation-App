package com.mckcieply.renovationapp.serviceTests;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.room.RoomRepository;
import com.mckcieply.renovationapp.room.RoomService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class RoomServiceTests extends BaseServiceTests<Room, RoomRepository> {

    @Mock
    private RoomRepository repository;

    @InjectMocks
    private RoomService service;

    @Override
    protected RoomRepository repository() {
        return repository;
    }

    @Override
    protected BaseService<Room, Long> service() {
        return service;
    }

    @Override
    protected List<Room> createDummyEntities() {
        List<Room> entities = new ArrayList<>();
        entities.add(Room.builder().id(1L).name("Living room").budgetPlanned(1500L).build());
        entities.add(Room.builder().id(2L).name("Kitchen").budgetPlanned(3000L).build());
        entities.add(Room.builder().id(3L).name("Bedroom").budgetPlanned(5000L).build());
        return entities;
    }

    @Override
    protected Room createDummyEntity() {
        return Room.builder().id(1L).name("Living room").budgetPlanned(1500L).build();
    }
}
