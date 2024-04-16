package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.room.RoomController;
import com.mckcieply.renovationapp.room.RoomService;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class RoomControllerTests extends BaseControllerTests<Room, RoomService>{

    @Mock
    private RoomService service;

    @Mock
    private RoomController controller;

    @Override
    protected RoomService service() {
        return service;
    }

    @Override
    protected BaseController<Room, Long> controller() {
        return controller;
    }

    @Override
    protected List<Room> createDummyEntities() {
        List<Room> entities = new ArrayList<>();
        entities.add(Room.builder().id(1L).name("Living room").budgetPlanned(1500L).build());
        entities.add(Room.builder().id(2L).name("Kitchen").budgetPlanned(3000L).build());
        entities.add(Room.builder().id(3L).name("Bedroom").budgetPlanned(4500L).build());
        return entities;
    }

    @Override
    protected Room createDummyEntity() {
        return Room.builder().id(1L).name("Living room").budgetPlanned(1500L).build();
    }
}
