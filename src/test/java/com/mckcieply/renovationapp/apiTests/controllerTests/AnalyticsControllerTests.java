package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.renovationapp.analytics.AnalyticsController;
import com.mckcieply.renovationapp.analytics.AnalyticsService;
import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnalyticsControllerTests {

    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private AnalyticsController analyticsController;

    @Test
    public void testGetCostBreakdown() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(15000.0, 10000.0);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(15000.0, response.getBody().getLaborTotal());
        assertEquals(10000.0, response.getBody().getMaterialTotal());
        verify(analyticsService, times(1)).getCostData();
    }

    @Test
    public void testGetCostBreakdownWithNullValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(null, null);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(0.0, response.getBody().getLaborTotal());
        assertEquals(0.0, response.getBody().getMaterialTotal());
        verify(analyticsService, times(1)).getCostData();
    }

    @Test
    public void testGetCostBreakdownWithZeroValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(0.0, 0.0);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0.0, response.getBody().getLaborTotal());
        assertEquals(0.0, response.getBody().getMaterialTotal());
        verify(analyticsService, times(1)).getCostData();
    }

    @Test
    public void testGetCostBreakdownWithLargeValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(999999.99, 888888.88);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(999999.99, response.getBody().getLaborTotal(), 0.01);
        assertEquals(888888.88, response.getBody().getMaterialTotal(), 0.01);
        verify(analyticsService, times(1)).getCostData();
    }

    @Test
    public void testGetRoomHealth() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Living Room", 15000.0, 12000.0),
            new RoomHealthDto("Kitchen", 20000.0, 18000.0),
            new RoomHealthDto("Bathroom", 8000.0, 7500.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedList, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());

        RoomHealthDto firstRoom = response.getBody().get(0);
        assertEquals("Living Room", firstRoom.getRoomName());
        assertEquals(15000.0, firstRoom.getBudgetPlanned());
        assertEquals(12000.0, firstRoom.getActualSpent());

        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testGetRoomHealthWithEmptyList() {
        // Arrange
        when(analyticsService.getRoomHealth()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testGetRoomHealthWithSingleRoom() {
        // Arrange
        List<RoomHealthDto> expectedList = List.of(
            new RoomHealthDto("Master Bedroom", 25000.0, 23000.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Master Bedroom", response.getBody().get(0).getRoomName());
        assertEquals(25000.0, response.getBody().get(0).getBudgetPlanned());
        assertEquals(23000.0, response.getBody().get(0).getActualSpent());
        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testGetRoomHealthWithOverBudgetRooms() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Over Budget Room", 5000.0, 6000.0),
            new RoomHealthDto("Another Over Budget", 3000.0, 4500.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        // Check first over-budget room
        RoomHealthDto firstRoom = response.getBody().get(0);
        assertTrue(firstRoom.getActualSpent() > firstRoom.getBudgetPlanned());
        assertEquals("Over Budget Room", firstRoom.getRoomName());

        // Check second over-budget room
        RoomHealthDto secondRoom = response.getBody().get(1);
        assertTrue(secondRoom.getActualSpent() > secondRoom.getBudgetPlanned());
        assertEquals("Another Over Budget", secondRoom.getRoomName());

        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testGetRoomHealthWithMixedBudgetScenarios() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Under Budget", 10000.0, 8000.0),
            new RoomHealthDto("Over Budget", 5000.0, 6000.0),
            new RoomHealthDto("Exactly On Budget", 7000.0, 7000.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());

        RoomHealthDto underBudget = response.getBody().get(0);
        assertTrue(underBudget.getActualSpent() < underBudget.getBudgetPlanned());
        assertEquals("Under Budget", underBudget.getRoomName());

        RoomHealthDto overBudget = response.getBody().get(1);
        assertTrue(overBudget.getActualSpent() > overBudget.getBudgetPlanned());
        assertEquals("Over Budget", overBudget.getRoomName());

        RoomHealthDto exactBudget = response.getBody().get(2);
        assertEquals(exactBudget.getActualSpent(), exactBudget.getBudgetPlanned());
        assertEquals("Exactly On Budget", exactBudget.getRoomName());

        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testGetRoomHealthWithSpecialCharacters() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Pokój #1 - Łazienka", 8000.0, 7500.0),
            new RoomHealthDto("Salon & Jadalnia", 15000.0, 14000.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Pokój #1 - Łazienka", response.getBody().get(0).getRoomName());
        assertEquals("Salon & Jadalnia", response.getBody().get(1).getRoomName());
        verify(analyticsService, times(1)).getRoomHealth();
    }

    @Test
    public void testMultipleCallsToGetCostBreakdown() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(5000.0, 3000.0);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response1 = analyticsController.getCostBreakdown();
        ResponseEntity<CostAnalyticsDto> response2 = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(expectedDto, response1.getBody());
        assertEquals(expectedDto, response2.getBody());
        verify(analyticsService, times(2)).getCostData();
    }

    @Test
    public void testMultipleCallsToGetRoomHealth() {
        // Arrange
        List<RoomHealthDto> expectedList = List.of(
            new RoomHealthDto("Test Room", 1000.0, 800.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response1 = analyticsController.getRoomHealth();
        ResponseEntity<List<RoomHealthDto>> response2 = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(expectedList, response1.getBody());
        assertEquals(expectedList, response2.getBody());
        verify(analyticsService, times(2)).getRoomHealth();
    }

    @Test
    public void testCostBreakdownResponseStructure() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(7500.0, 4500.0);
        when(analyticsService.getCostData()).thenReturn(expectedDto);

        // Act
        ResponseEntity<CostAnalyticsDto> response = analyticsController.getCostBreakdown();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(7500.0, response.getBody().getLaborTotal());
        assertEquals(4500.0, response.getBody().getMaterialTotal());
    }

    @Test
    public void testRoomHealthResponseStructure() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Room 1", 1000.0, 900.0),
            new RoomHealthDto("Room 2", 2000.0, 1800.0)
        );
        when(analyticsService.getRoomHealth()).thenReturn(expectedList);

        // Act
        ResponseEntity<List<RoomHealthDto>> response = analyticsController.getRoomHealth();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(2, response.getBody().size());
        assertInstanceOf(List.class, response.getBody());
    }
}
