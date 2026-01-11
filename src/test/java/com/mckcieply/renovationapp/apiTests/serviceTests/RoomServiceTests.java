package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.room.RoomBudgetDetailDto;
import com.mckcieply.renovationapp.room.RoomRepository;
import com.mckcieply.renovationapp.room.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void testCanDeleteRoom_WithNoWork() {
        // Arrange
        Long roomId = 1L;
        when(repository.hasAssociatedWork(roomId)).thenReturn(false);

        // Act
        boolean result = service.canDeleteRoom(roomId);

        // Assert
        assertTrue(result);
        verify(repository, times(1)).hasAssociatedWork(roomId);
    }

    @Test
    public void testCanDeleteRoom_WithAssociatedWork() {
        // Arrange
        Long roomId = 1L;
        when(repository.hasAssociatedWork(roomId)).thenReturn(true);

        // Act
        boolean result = service.canDeleteRoom(roomId);

        // Assert
        assertFalse(result);
        verify(repository, times(1)).hasAssociatedWork(roomId);
    }

    @Test
    public void testGetRoomBudgetDetails() {
        // Arrange
        List<RoomBudgetDetailDto> mockDetails = Arrays.asList(
            new RoomBudgetDetailDto(1L, "Living Room", 15000.0, 8000.0, 5000.0, 0.0, true),
            new RoomBudgetDetailDto(2L, "Kitchen", 25000.0, 12000.0, 8000.0, 0.0, true),
            new RoomBudgetDetailDto(3L, "Bedroom", 10000.0, 3000.0, 2000.0, 0.0, false)
        );

        when(repository.getRoomBudgetDetails()).thenReturn(mockDetails);

        // Act
        List<RoomBudgetDetailDto> result = service.getRoomBudgetDetails();

        // Assert
        assertEquals(3, result.size());

        // Check calculated unallocated values
        assertEquals(2000.0, result.get(0).getUnallocated()); // 15000 - 8000 - 5000
        assertEquals(5000.0, result.get(1).getUnallocated()); // 25000 - 12000 - 8000
        assertEquals(5000.0, result.get(2).getUnallocated()); // 10000 - 3000 - 2000

        verify(repository, times(1)).getRoomBudgetDetails();
    }

    @Test
    public void testGetRoomBudgetDetails_WithNegativeUnallocated() {
        // Arrange - Room where costs exceed budget
        List<RoomBudgetDetailDto> mockDetails = List.of(
            new RoomBudgetDetailDto(1L, "Living Room", 10000.0, 8000.0, 5000.0, 0.0, true) // 13000 > 10000
        );

        when(repository.getRoomBudgetDetails()).thenReturn(mockDetails);

        // Act
        List<RoomBudgetDetailDto> result = service.getRoomBudgetDetails();

        // Assert
        assertEquals(1, result.size());
        assertEquals(0.0, result.get(0).getUnallocated()); // Should be 0, not negative
        verify(repository, times(1)).getRoomBudgetDetails();
    }

    @Test
    public void testGetRoomBudgetDetails_EmptyList() {
        // Arrange
        when(repository.getRoomBudgetDetails()).thenReturn(Collections.emptyList());

        // Act
        List<RoomBudgetDetailDto> result = service.getRoomBudgetDetails();

        // Assert
        assertTrue(result.isEmpty());
        verify(repository, times(1)).getRoomBudgetDetails();
    }

    @Test
    public void testGetTotalRoomBudgets() {
        // Arrange
        List<Room> rooms = Arrays.asList(
            Room.builder().id(1L).name("Living Room").budgetPlanned(15000L).build(),
            Room.builder().id(2L).name("Kitchen").budgetPlanned(25000L).build(),
            Room.builder().id(3L).name("Bedroom").budgetPlanned(10000L).build()
        );

        when(repository.findAll()).thenReturn(rooms);

        // Act
        double result = service.getTotalRoomBudgets();

        // Assert
        assertEquals(50000.0, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetTotalRoomBudgets_WithNullBudgets() {
        // Arrange
        List<Room> rooms = Arrays.asList(
            Room.builder().id(1L).name("Living Room").budgetPlanned(15000L).build(),
            Room.builder().id(2L).name("Kitchen").budgetPlanned(null).build(), // null budget
            Room.builder().id(3L).name("Bedroom").budgetPlanned(10000L).build()
        );

        when(repository.findAll()).thenReturn(rooms);

        // Act
        double result = service.getTotalRoomBudgets();

        // Assert
        assertEquals(25000.0, result); // null treated as 0
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetTotalRoomBudgets_EmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(Collections.emptyList());

        // Act
        double result = service.getTotalRoomBudgets();

        // Assert
        assertEquals(0.0, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testDelete_CanDelete() {
        // Arrange
        Long roomId = 1L;
        when(repository.hasAssociatedWork(roomId)).thenReturn(false);

        // Act
        service.delete(roomId);

        // Assert
        verify(repository, times(1)).hasAssociatedWork(roomId);
        verify(repository, times(1)).deleteById(roomId);
    }

    @Test
    public void testDelete_CannotDelete() {
        // Arrange
        Long roomId = 1L;
        when(repository.hasAssociatedWork(roomId)).thenReturn(true);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> service.delete(roomId));

        assertEquals("Cannot delete room - it has associated work items", exception.getMessage());
        verify(repository, times(1)).hasAssociatedWork(roomId);
        verify(repository, never()).deleteById(roomId);
    }

    @Test
    public void testConstructor() {
        // Arrange
        RoomRepository mockRepo = mock(RoomRepository.class);

        // Act
        RoomService newService = new RoomService(mockRepo);

        // Assert
        assertNotNull(newService);
    }

}
