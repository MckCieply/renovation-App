package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.renovationapp.analytics.AnalyticsService;
import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import com.mckcieply.renovationapp.room.RoomRepository;
import com.mckcieply.renovationapp.work.WorkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnalyticsServiceTests {

    @Mock
    private WorkRepository workRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private AnalyticsService analyticsService;

    @Test
    public void testGetCostData() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(15000.0, 10000.0);
        when(workRepository.getCostAnalytics()).thenReturn(expectedDto);

        // Act
        CostAnalyticsDto result = analyticsService.getCostData();

        // Assert
        assertEquals(expectedDto, result);
        assertEquals(15000.0, result.getLaborTotal());
        assertEquals(10000.0, result.getMaterialTotal());
        verify(workRepository, times(1)).getCostAnalytics();
    }

    @Test
    public void testGetCostDataWithNullValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(null, null);
        when(workRepository.getCostAnalytics()).thenReturn(expectedDto);

        // Act
        CostAnalyticsDto result = analyticsService.getCostData();

        // Assert
        assertEquals(expectedDto, result);
        assertEquals(0.0, result.getLaborTotal());
        assertEquals(0.0, result.getMaterialTotal());
        verify(workRepository, times(1)).getCostAnalytics();
    }

    @Test
    public void testGetCostDataWithZeroValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(0.0, 0.0);
        when(workRepository.getCostAnalytics()).thenReturn(expectedDto);

        // Act
        CostAnalyticsDto result = analyticsService.getCostData();

        // Assert
        assertEquals(expectedDto, result);
        assertEquals(0.0, result.getLaborTotal());
        assertEquals(0.0, result.getMaterialTotal());
        verify(workRepository, times(1)).getCostAnalytics();
    }

    @Test
    public void testGetRoomHealth() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Living Room", 15000.0, 12000.0, 10000.0),
            new RoomHealthDto("Kitchen", 20000.0, 18000.0, 15000.0),
            new RoomHealthDto("Bathroom", 8000.0, 7500.0, 6000.0)
        );
        when(roomRepository.getRoomHealthData()).thenReturn(expectedList);

        // Act
        List<RoomHealthDto> result = analyticsService.getRoomHealth();

        // Assert
        assertEquals(expectedList, result);
        assertEquals(3, result.size());

        RoomHealthDto firstRoom = result.get(0);
        assertEquals("Living Room", firstRoom.getRoomName());
        assertEquals(15000.0, firstRoom.getBudgetPlanned());
        assertEquals(12000.0, firstRoom.getEstimatedCost());
        assertEquals(10000.0, firstRoom.getPaidCost());

        verify(roomRepository, times(1)).getRoomHealthData();
    }

    @Test
    public void testGetRoomHealthWithEmptyList() {
        // Arrange
        when(roomRepository.getRoomHealthData()).thenReturn(Collections.emptyList());

        // Act
        List<RoomHealthDto> result = analyticsService.getRoomHealth();

        // Assert
        assertTrue(result.isEmpty());
        verify(roomRepository, times(1)).getRoomHealthData();
    }

    @Test
    public void testGetRoomHealthWithSingleRoom() {
        // Arrange
        List<RoomHealthDto> expectedList = List.of(
            new RoomHealthDto("Master Bedroom", 25000.0, 23000.0, 20000.0)
        );
        when(roomRepository.getRoomHealthData()).thenReturn(expectedList);

        // Act
        List<RoomHealthDto> result = analyticsService.getRoomHealth();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Master Bedroom", result.get(0).getRoomName());
        assertEquals(25000.0, result.get(0).getBudgetPlanned());
        assertEquals(23000.0, result.get(0).getEstimatedCost());
        assertEquals(20000.0, result.get(0).getPaidCost());
        verify(roomRepository, times(1)).getRoomHealthData();
    }

    @Test
    public void testGetRoomHealthWithOverBudgetRooms() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Over Budget Room", 5000.0, 6000.0, 5500.0),
            new RoomHealthDto("Another Over Budget", 3000.0, 4500.0, 4000.0)
        );
        when(roomRepository.getRoomHealthData()).thenReturn(expectedList);

        // Act
        List<RoomHealthDto> result = analyticsService.getRoomHealth();

        // Assert
        assertEquals(2, result.size());

        // Check first over-budget room
        RoomHealthDto firstRoom = result.get(0);
        assertTrue(firstRoom.getEstimatedCost() > firstRoom.getBudgetPlanned());

        // Check second over-budget room
        RoomHealthDto secondRoom = result.get(1);
        assertTrue(secondRoom.getEstimatedCost() > secondRoom.getBudgetPlanned());

        verify(roomRepository, times(1)).getRoomHealthData();
    }

    @Test
    public void testGetRoomHealthWithMixedBudgetScenarios() {
        // Arrange
        List<RoomHealthDto> expectedList = Arrays.asList(
            new RoomHealthDto("Under Budget", 10000.0, 8000.0, 7000.0),
            new RoomHealthDto("Over Budget", 5000.0, 6000.0, 5500.0),
            new RoomHealthDto("Exactly On Budget", 7000.0, 7000.0, 6500.0)
        );
        when(roomRepository.getRoomHealthData()).thenReturn(expectedList);

        // Act
        List<RoomHealthDto> result = analyticsService.getRoomHealth();

        // Assert
        assertEquals(3, result.size());

        RoomHealthDto underBudget = result.get(0);
        assertTrue(underBudget.getEstimatedCost() < underBudget.getBudgetPlanned());

        RoomHealthDto overBudget = result.get(1);
        assertTrue(overBudget.getEstimatedCost() > overBudget.getBudgetPlanned());

        RoomHealthDto exactBudget = result.get(2);
        assertEquals(exactBudget.getEstimatedCost(), exactBudget.getBudgetPlanned());

        verify(roomRepository, times(1)).getRoomHealthData();
    }

    @Test
    public void testGetCostDataWithLargeValues() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(999999.99, 888888.88);
        when(workRepository.getCostAnalytics()).thenReturn(expectedDto);

        // Act
        CostAnalyticsDto result = analyticsService.getCostData();

        // Assert
        assertEquals(expectedDto, result);
        assertEquals(999999.99, result.getLaborTotal(), 0.01);
        assertEquals(888888.88, result.getMaterialTotal(), 0.01);
        verify(workRepository, times(1)).getCostAnalytics();
    }

    @Test
    public void testGetCostDataMultipleCalls() {
        // Arrange
        CostAnalyticsDto expectedDto = new CostAnalyticsDto(5000.0, 3000.0);
        when(workRepository.getCostAnalytics()).thenReturn(expectedDto);

        // Act
        CostAnalyticsDto result1 = analyticsService.getCostData();
        CostAnalyticsDto result2 = analyticsService.getCostData();

        // Assert
        assertEquals(expectedDto, result1);
        assertEquals(expectedDto, result2);
        verify(workRepository, times(2)).getCostAnalytics();
    }

    @Test
    public void testGetRoomHealthMultipleCalls() {
        // Arrange
        List<RoomHealthDto> expectedList = List.of(
            new RoomHealthDto("Test Room", 1000.0, 800.0, 600.0)
        );
        when(roomRepository.getRoomHealthData()).thenReturn(expectedList);

        // Act
        List<RoomHealthDto> result1 = analyticsService.getRoomHealth();
        List<RoomHealthDto> result2 = analyticsService.getRoomHealth();

        // Assert
        assertEquals(expectedList, result1);
        assertEquals(expectedList, result2);
        verify(roomRepository, times(2)).getRoomHealthData();
    }

}
