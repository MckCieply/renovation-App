package com.mckcieply.renovationapp.apiTests.dtoTests;

import com.mckcieply.renovationapp.budget.Budget;
import com.mckcieply.renovationapp.budget.BudgetValidationDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetValidationDtoTests {

    private Budget createMockBudget(double limit) {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setBudgetLimit(limit);
        return budget;
    }

    @Test
    public void testFromMethodWithinLimit() {
        // Arrange
        Budget budget = createMockBudget(100000.0);
        double totalRoomBudgets = 75000.0;
        double totalEstimatedCosts = 65000.0;
        double totalPaidCosts = 30000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(100000.0, result.getBudgetLimit());
        assertEquals(75000.0, result.getTotalRoomBudgets());
        assertEquals(65000.0, result.getTotalEstimatedCosts());
        assertEquals(30000.0, result.getTotalPaidCosts());
        assertEquals(25000.0, result.getAvailableBudget()); // 100000 - 75000
        assertFalse(result.isOverallocated());
        assertNull(result.getWarningMessage());
    }

    @Test
    public void testFromMethodOverallocated() {
        // Arrange
        Budget budget = createMockBudget(50000.0);
        double totalRoomBudgets = 75000.0;
        double totalEstimatedCosts = 65000.0;
        double totalPaidCosts = 30000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(50000.0, result.getBudgetLimit());
        assertEquals(75000.0, result.getTotalRoomBudgets());
        assertEquals(65000.0, result.getTotalEstimatedCosts());
        assertEquals(30000.0, result.getTotalPaidCosts());
        assertEquals(-25000.0, result.getAvailableBudget()); // 50000 - 75000
        assertTrue(result.isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 25000.00 PLN", result.getWarningMessage());
    }

    @Test
    public void testFromMethodExactlyAtLimit() {
        // Arrange
        Budget budget = createMockBudget(75000.0);
        double totalRoomBudgets = 75000.0;
        double totalEstimatedCosts = 60000.0;
        double totalPaidCosts = 25000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(75000.0, result.getBudgetLimit());
        assertEquals(75000.0, result.getTotalRoomBudgets());
        assertEquals(0.0, result.getAvailableBudget()); // 75000 - 75000
        assertFalse(result.isOverallocated()); // Exactly at limit is not overallocated
        assertNull(result.getWarningMessage());
    }

    @Test
    public void testFromMethodWithZeroBudget() {
        // Arrange
        Budget budget = createMockBudget(0.0);
        double totalRoomBudgets = 50000.0;
        double totalEstimatedCosts = 40000.0;
        double totalPaidCosts = 15000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(0.0, result.getBudgetLimit());
        assertEquals(50000.0, result.getTotalRoomBudgets());
        assertEquals(-50000.0, result.getAvailableBudget()); // 0 - 50000
        assertTrue(result.isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 50000.00 PLN", result.getWarningMessage());
    }

    @Test
    public void testFromMethodWithNegativeBudget() {
        // Arrange
        Budget budget = createMockBudget(-10000.0);
        double totalRoomBudgets = 20000.0;
        double totalEstimatedCosts = 15000.0;
        double totalPaidCosts = 5000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(-10000.0, result.getBudgetLimit());
        assertEquals(20000.0, result.getTotalRoomBudgets());
        assertEquals(-30000.0, result.getAvailableBudget()); // -10000 - 20000
        assertTrue(result.isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 30000.00 PLN", result.getWarningMessage());
    }

    @Test
    public void testFromMethodWithZeroRoomBudgets() {
        // Arrange
        Budget budget = createMockBudget(100000.0);
        double totalRoomBudgets = 0.0;
        double totalEstimatedCosts = 0.0;
        double totalPaidCosts = 0.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(100000.0, result.getBudgetLimit());
        assertEquals(0.0, result.getTotalRoomBudgets());
        assertEquals(0.0, result.getTotalEstimatedCosts());
        assertEquals(0.0, result.getTotalPaidCosts());
        assertEquals(100000.0, result.getAvailableBudget()); // 100000 - 0
        assertFalse(result.isOverallocated());
        assertNull(result.getWarningMessage());
    }

    @Test
    public void testFromMethodSmallOverallocation() {
        // Arrange
        Budget budget = createMockBudget(100000.0);
        double totalRoomBudgets = 100000.01; // Very small overallocation
        double totalEstimatedCosts = 80000.0;
        double totalPaidCosts = 40000.0;

        // Act
        BudgetValidationDto result = BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimatedCosts, totalPaidCosts);

        // Assert
        assertEquals(100000.0, result.getBudgetLimit());
        assertEquals(100000.01, result.getTotalRoomBudgets());
        assertEquals(-0.00999999999476837, result.getAvailableBudget(), 0.001); // Small negative
        assertTrue(result.isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 0.01 PLN", result.getWarningMessage());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange & Act
        BudgetValidationDto dto = new BudgetValidationDto(
            100000.0, 75000.0, 65000.0, 30000.0, 25000.0, false, null
        );

        // Assert
        assertEquals(100000.0, dto.getBudgetLimit());
        assertEquals(75000.0, dto.getTotalRoomBudgets());
        assertEquals(65000.0, dto.getTotalEstimatedCosts());
        assertEquals(30000.0, dto.getTotalPaidCosts());
        assertEquals(25000.0, dto.getAvailableBudget());
        assertFalse(dto.isOverallocated());
        assertNull(dto.getWarningMessage());
    }

    @Test
    public void testNoArgsConstructor() {
        // Act
        BudgetValidationDto dto = new BudgetValidationDto();

        // Assert
        assertEquals(0.0, dto.getBudgetLimit());
        assertEquals(0.0, dto.getTotalRoomBudgets());
        assertEquals(0.0, dto.getTotalEstimatedCosts());
        assertEquals(0.0, dto.getTotalPaidCosts());
        assertEquals(0.0, dto.getAvailableBudget());
        assertFalse(dto.isOverallocated());
        assertNull(dto.getWarningMessage());
    }

    @Test
    public void testSettersAndGetters() {
        // Arrange
        BudgetValidationDto dto = new BudgetValidationDto();

        // Act
        dto.setBudgetLimit(120000.0);
        dto.setTotalRoomBudgets(80000.0);
        dto.setTotalEstimatedCosts(70000.0);
        dto.setTotalPaidCosts(35000.0);
        dto.setAvailableBudget(40000.0);
        dto.setOverallocated(true);
        dto.setWarningMessage("Test warning");

        // Assert
        assertEquals(120000.0, dto.getBudgetLimit());
        assertEquals(80000.0, dto.getTotalRoomBudgets());
        assertEquals(70000.0, dto.getTotalEstimatedCosts());
        assertEquals(35000.0, dto.getTotalPaidCosts());
        assertEquals(40000.0, dto.getAvailableBudget());
        assertTrue(dto.isOverallocated());
        assertEquals("Test warning", dto.getWarningMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        BudgetValidationDto dto1 = new BudgetValidationDto(
            100000.0, 75000.0, 65000.0, 30000.0, 25000.0, false, null
        );
        BudgetValidationDto dto2 = new BudgetValidationDto(
            100000.0, 75000.0, 65000.0, 30000.0, 25000.0, false, null
        );
        BudgetValidationDto dto3 = new BudgetValidationDto(
            200000.0, 75000.0, 65000.0, 30000.0, 25000.0, false, null
        );

        // Assert
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
    }
}
