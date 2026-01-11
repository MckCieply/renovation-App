package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.work.Work;
import com.mckcieply.renovationapp.work.WorkController;
import com.mckcieply.renovationapp.work.WorkFilter;
import com.mckcieply.renovationapp.work.WorkService;
import com.mckcieply.renovationapp.workType.WorkType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorkControllerTests extends BaseControllerTests<Work, WorkService>{

    @Mock
    private WorkService service;

    @InjectMocks
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

    @Test
    public void testGetEnumWorkState() {
        // Act
        ResponseEntity<EnumWorkState[]> response = controller.getEnumWorkState();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().length);

        EnumWorkState[] states = response.getBody();
        assertTrue(Arrays.asList(states).contains(EnumWorkState.PLANNED));
        assertTrue(Arrays.asList(states).contains(EnumWorkState.IN_PROGRESS));
        assertTrue(Arrays.asList(states).contains(EnumWorkState.FINISHED));
    }

    @Test
    public void testGetFilteredWithResults() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setState(EnumWorkState.IN_PROGRESS);
        filter.setPaid(false);

        List<Work> filteredWorks = createDummyEntities();
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(filteredWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(filteredWorks, response.getBody());
        assertEquals(3, response.getBody().size());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredNoResults() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setState(EnumWorkState.FINISHED);
        filter.setPaid(true);

        when(service.getFiltered(any(WorkFilter.class))).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredWithEmptyFilter() {
        // Arrange
        WorkFilter emptyFilter = new WorkFilter();
        List<Work> allWorks = createDummyEntities();
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(allWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(emptyFilter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(allWorks, response.getBody());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredWithSpecificRoom() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setRoomId(1L);

        List<Work> roomWorks = List.of(createDummyEntities().get(0));
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(roomWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredWithSpecificWorkType() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setWorkTypeId(2L);

        List<Work> workTypeWorks = List.of(createDummyEntities().get(1));
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(workTypeWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredWithDescription() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setDescription("painting");

        List<Work> descriptionWorks = List.of(createDummyEntities().get(0));
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(descriptionWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetFilteredWithMultipleCriteria() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setState(EnumWorkState.IN_PROGRESS);
        filter.setPaid(false);
        filter.setRoomId(1L);
        filter.setWorkTypeId(1L);
        filter.setDescription("walls");

        List<Work> filteredWorks = List.of(createDummyEntities().get(0));
        when(service.getFiltered(any(WorkFilter.class))).thenReturn(filteredWorks);

        // Act
        ResponseEntity<List<Work>> response = controller.getFiltered(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getFiltered(any(WorkFilter.class));
    }

    @Test
    public void testGetEnumWorkStateValues() {
        // Act
        ResponseEntity<EnumWorkState[]> response = controller.getEnumWorkState();
        EnumWorkState[] states = response.getBody();

        // Assert
        assertNotNull(states);
        assertEquals(EnumWorkState.values().length, states.length);

        // Verify each enum value is present
        for (EnumWorkState expectedState : EnumWorkState.values()) {
            assertTrue(Arrays.asList(states).contains(expectedState),
                "Missing enum state: " + expectedState);
        }
    }

    @Test
    public void testGetFilteredWithPaidFilter() {
        // Arrange
        WorkFilter paidFilter = new WorkFilter();
        paidFilter.setPaid(true);

        WorkFilter unpaidFilter = new WorkFilter();
        unpaidFilter.setPaid(false);

        List<Work> paidWorks = List.of(createDummyEntities().get(1)); // assuming second work is paid
        List<Work> unpaidWorks = List.of(createDummyEntities().get(0)); // assuming first work is unpaid

        when(service.getFiltered(eq(paidFilter))).thenReturn(paidWorks);
        when(service.getFiltered(eq(unpaidFilter))).thenReturn(unpaidWorks);

        // Act
        ResponseEntity<List<Work>> paidResponse = controller.getFiltered(paidFilter);
        ResponseEntity<List<Work>> unpaidResponse = controller.getFiltered(unpaidFilter);

        // Assert
        assertEquals(HttpStatus.OK, paidResponse.getStatusCode());
        assertEquals(HttpStatus.OK, unpaidResponse.getStatusCode());
        assertNotNull(paidResponse.getBody());
        assertNotNull(unpaidResponse.getBody());
        assertEquals(1, paidResponse.getBody().size());
        assertEquals(1, unpaidResponse.getBody().size());
        verify(service, times(1)).getFiltered(eq(paidFilter));
        verify(service, times(1)).getFiltered(eq(unpaidFilter));
    }
}
