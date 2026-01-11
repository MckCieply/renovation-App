package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.room.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomControllerTests extends BaseControllerTests<Room, RoomService>{

    @Mock
    private RoomService service;

    @InjectMocks
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

    @Test
    public void testGetFiltered_WithResults() {
        // Arrange
        RoomFilter filter = new RoomFilter();
        filter.setMaxBudgetPlanned(5000L);
        List<Room> filteredRooms = createDummyEntities();
        when(service.getFiltered(any(RoomFilter.class))).thenReturn(filteredRooms);

        // Act
        ResponseEntity<List<Room>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(filteredRooms, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        verify(service, times(1)).getFiltered(any(RoomFilter.class));
    }

    @Test
    public void testGetFiltered_NoResults() {
        // Arrange
        RoomFilter filter = new RoomFilter();
        filter.setMinBudgetPlanned(10000L);
        when(service.getFiltered(any(RoomFilter.class))).thenReturn(new ArrayList<>());

        // Act
        ResponseEntity<List<Room>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).getFiltered(any(RoomFilter.class));
    }

    @Test
    public void testCanDeleteRoom_CanDelete() {
        // Arrange
        Long roomId = 1L;
        when(service.canDeleteRoom(roomId)).thenReturn(true);

        // Act
        ResponseEntity<Map<String, Object>> response = controller.canDeleteRoom(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue((Boolean) response.getBody().get("canDelete"));
        assertEquals("", response.getBody().get("message"));
        verify(service, times(1)).canDeleteRoom(roomId);
    }

    @Test
    public void testCanDeleteRoom_CannotDelete() {
        // Arrange
        Long roomId = 1L;
        when(service.canDeleteRoom(roomId)).thenReturn(false);

        // Act
        ResponseEntity<Map<String, Object>> response = controller.canDeleteRoom(roomId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse((Boolean) response.getBody().get("canDelete"));
        assertEquals("Cannot delete room - it has associated work items", response.getBody().get("message"));
        verify(service, times(1)).canDeleteRoom(roomId);
    }

    @Test
    public void testGetRoomBudgetDetails() {
        // Arrange
        List<RoomBudgetDetailDto> budgetDetails = Arrays.asList(
            new RoomBudgetDetailDto(1L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true),
            new RoomBudgetDetailDto(2L, "Kitchen", 25000.0, 12000.0, 8000.0, 5000.0, true)
        );
        when(service.getRoomBudgetDetails()).thenReturn(budgetDetails);

        // Act
        ResponseEntity<List<RoomBudgetDetailDto>> response = controller.getRoomBudgetDetails();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budgetDetails, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(service, times(1)).getRoomBudgetDetails();
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        Long roomId = 1L;
        doNothing().when(service).delete(roomId);

        // Act
        ResponseEntity<Void> response = controller.delete(roomId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(roomId);
    }

    @Test
    public void testDelete_FailureDueToAssociatedWork() {
        // Arrange
        Long roomId = 1L;
        doThrow(new IllegalStateException("Cannot delete room - it has associated work items"))
            .when(service).delete(roomId);

        // Act
        ResponseEntity<Void> response = controller.delete(roomId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(service, times(1)).delete(roomId);
    }

    @Test
    public void testGetFilteredWithEmptyFilter() {
        // Arrange
        RoomFilter emptyFilter = new RoomFilter();
        List<Room> allRooms = createDummyEntities();
        when(service.getFiltered(any(RoomFilter.class))).thenReturn(allRooms);

        // Act
        ResponseEntity<List<Room>> response = controller.getFiltered(emptyFilter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allRooms, response.getBody());
        verify(service, times(1)).getFiltered(any(RoomFilter.class));
    }

    @Test
    public void testGetFilteredWithBudgetRange() {
        // Arrange
        RoomFilter filter = new RoomFilter();
        filter.setMinBudgetPlanned(2000L);
        filter.setMaxBudgetPlanned(4000L);

        List<Room> filteredRooms = List.of(
            Room.builder().id(2L).name("Kitchen").budgetPlanned(3000L).build()
        );
        when(service.getFiltered(any(RoomFilter.class))).thenReturn(filteredRooms);

        // Act
        ResponseEntity<List<Room>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Kitchen", response.getBody().get(0).getName());
        verify(service, times(1)).getFiltered(any(RoomFilter.class));
    }

}
