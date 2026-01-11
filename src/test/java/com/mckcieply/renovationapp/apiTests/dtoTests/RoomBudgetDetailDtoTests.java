package com.mckcieply.renovationapp.apiTests.dtoTests;

import com.mckcieply.renovationapp.room.RoomBudgetDetailDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomBudgetDetailDtoTests {

    @Test
    public void testAllArgsConstructor() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true
        );

        // Assert
        assertEquals(1L, dto.getRoomId());
        assertEquals("Living Room", dto.getRoomName());
        assertEquals(15000.0, dto.getBudgetPlanned());
        assertEquals(8000.0, dto.getEstimatedCosts());
        assertEquals(5000.0, dto.getPaidCosts());
        assertEquals(2000.0, dto.getUnallocated());
        assertTrue(dto.isHasActiveWork());
    }

    @Test
    public void testNoArgsConstructor() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto();

        // Assert
        assertNull(dto.getRoomId());
        assertNull(dto.getRoomName());
        assertEquals(0.0, dto.getBudgetPlanned());
        assertEquals(0.0, dto.getEstimatedCosts());
        assertEquals(0.0, dto.getPaidCosts());
        assertEquals(0.0, dto.getUnallocated());
        assertFalse(dto.isHasActiveWork());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto();

        // Act
        dto.setRoomId(2L);
        dto.setRoomName("Kitchen");
        dto.setBudgetPlanned(25000.0);
        dto.setEstimatedCosts(12000.0);
        dto.setPaidCosts(8000.0);
        dto.setUnallocated(5000.0);
        dto.setHasActiveWork(true);

        // Assert
        assertEquals(2L, dto.getRoomId());
        assertEquals("Kitchen", dto.getRoomName());
        assertEquals(25000.0, dto.getBudgetPlanned());
        assertEquals(12000.0, dto.getEstimatedCosts());
        assertEquals(8000.0, dto.getPaidCosts());
        assertEquals(5000.0, dto.getUnallocated());
        assertTrue(dto.isHasActiveWork());
    }

    @Test
    public void testWithZeroValues() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            0L, "", 0.0, 0.0, 0.0, 0.0, false
        );

        // Assert
        assertEquals(0L, dto.getRoomId());
        assertEquals("", dto.getRoomName());
        assertEquals(0.0, dto.getBudgetPlanned());
        assertEquals(0.0, dto.getEstimatedCosts());
        assertEquals(0.0, dto.getPaidCosts());
        assertEquals(0.0, dto.getUnallocated());
        assertFalse(dto.isHasActiveWork());
    }

    @Test
    public void testWithNegativeValues() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            -1L, "Test Room", -1000.0, -500.0, -300.0, -200.0, false
        );

        // Assert
        assertEquals(-1L, dto.getRoomId());
        assertEquals("Test Room", dto.getRoomName());
        assertEquals(-1000.0, dto.getBudgetPlanned());
        assertEquals(-500.0, dto.getEstimatedCosts());
        assertEquals(-300.0, dto.getPaidCosts());
        assertEquals(-200.0, dto.getUnallocated());
        assertFalse(dto.isHasActiveWork());
    }

    @Test
    public void testWithLargeValues() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            Long.MAX_VALUE, "Large Room", Double.MAX_VALUE,
            Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, true
        );

        // Assert
        assertEquals(Long.MAX_VALUE, dto.getRoomId());
        assertEquals("Large Room", dto.getRoomName());
        assertEquals(Double.MAX_VALUE, dto.getBudgetPlanned());
        assertEquals(Double.MAX_VALUE, dto.getEstimatedCosts());
        assertEquals(Double.MAX_VALUE, dto.getPaidCosts());
        assertEquals(Double.MAX_VALUE, dto.getUnallocated());
        assertTrue(dto.isHasActiveWork());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        RoomBudgetDetailDto dto1 = new RoomBudgetDetailDto(
            1L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true
        );
        RoomBudgetDetailDto dto2 = new RoomBudgetDetailDto(
            1L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true
        );
        RoomBudgetDetailDto dto3 = new RoomBudgetDetailDto(
            2L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true
        );

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, "Living Room", 15000.0, 8000.0, 5000.0, 2000.0, true
        );

        // Act
        String result = dto.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("RoomBudgetDetailDto"));
        assertTrue(result.contains("roomId=1"));
        assertTrue(result.contains("roomName=Living Room"));
        assertTrue(result.contains("budgetPlanned=15000.0"));
        assertTrue(result.contains("estimatedCosts=8000.0"));
        assertTrue(result.contains("paidCosts=5000.0"));
        assertTrue(result.contains("unallocated=2000.0"));
        assertTrue(result.contains("hasActiveWork=true"));
    }

    @Test
    public void testWithNullRoomName() {
        // Act
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, null, 15000.0, 8000.0, 5000.0, 2000.0, true
        );

        // Assert
        assertEquals(1L, dto.getRoomId());
        assertNull(dto.getRoomName());
        assertEquals(15000.0, dto.getBudgetPlanned());
        assertEquals(8000.0, dto.getEstimatedCosts());
        assertEquals(5000.0, dto.getPaidCosts());
        assertEquals(2000.0, dto.getUnallocated());
        assertTrue(dto.isHasActiveWork());
    }

    @Test
    public void testBudgetCalculationScenario_OverBudget() {
        // Arrange - Scenario where costs exceed budget
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, "Over Budget Room", 10000.0, 8000.0, 5000.0, 0.0, true
        );

        // Act & Assert
        // Total costs: 8000 + 5000 = 13000, Budget: 10000
        // This would normally result in negative unallocated, but service should set it to 0
        assertEquals(10000.0, dto.getBudgetPlanned());
        assertEquals(8000.0, dto.getEstimatedCosts());
        assertEquals(5000.0, dto.getPaidCosts());
        assertEquals(0.0, dto.getUnallocated()); // Should be 0, not negative
    }

    @Test
    public void testBudgetCalculationScenario_UnderBudget() {
        // Arrange - Scenario where budget exceeds costs
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, "Under Budget Room", 20000.0, 6000.0, 4000.0, 10000.0, false
        );

        // Act & Assert
        // Total costs: 6000 + 4000 = 10000, Budget: 20000
        // Unallocated should be: 20000 - 10000 = 10000
        assertEquals(20000.0, dto.getBudgetPlanned());
        assertEquals(6000.0, dto.getEstimatedCosts());
        assertEquals(4000.0, dto.getPaidCosts());
        assertEquals(10000.0, dto.getUnallocated());
        assertFalse(dto.isHasActiveWork());
    }

    @Test
    public void testDecimalPrecision() {
        // Arrange - Test with decimal values
        RoomBudgetDetailDto dto = new RoomBudgetDetailDto(
            1L, "Precise Room", 15000.99, 7999.50, 4999.25, 2002.24, true
        );

        // Assert
        assertEquals(15000.99, dto.getBudgetPlanned(), 0.01);
        assertEquals(7999.50, dto.getEstimatedCosts(), 0.01);
        assertEquals(4999.25, dto.getPaidCosts(), 0.01);
        assertEquals(2002.24, dto.getUnallocated(), 0.01);
    }
}
