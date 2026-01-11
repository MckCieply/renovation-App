package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.renovationapp.budget.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetControllerTests {

    @Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    private Budget createMockBudget(double limit) {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setBudgetLimit(limit);
        return budget;
    }

    private BudgetValidationDto createMockValidationDto(double budgetLimit, double totalRoomBudgets,
                                                       boolean overallocated, String warningMessage) {
        BudgetValidationDto dto = new BudgetValidationDto();
        dto.setBudgetLimit(budgetLimit);
        dto.setTotalRoomBudgets(totalRoomBudgets);
        dto.setTotalEstimatedCosts(40000.0);
        dto.setTotalPaidCosts(20000.0);
        dto.setAvailableBudget(budgetLimit - totalRoomBudgets);
        dto.setOverallocated(overallocated);
        dto.setWarningMessage(warningMessage);
        return dto;
    }

    @Test
    public void testGetBudget() {
        // Arrange
        Budget expectedBudget = createMockBudget(100000.0);
        when(budgetService.getBudget()).thenReturn(expectedBudget);

        // Act
        ResponseEntity<Budget> response = budgetController.getBudget();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBudget, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(100000.0, response.getBody().getBudgetLimit());
        verify(budgetService, times(1)).getBudget();
    }

    @Test
    public void testValidateBudget() {
        // Arrange
        BudgetValidationDto expectedValidation = createMockValidationDto(
            100000.0, 75000.0, false, null);
        when(budgetService.validateBudget()).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.validateBudget();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedValidation, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(100000.0, response.getBody().getBudgetLimit());
        assertEquals(75000.0, response.getBody().getTotalRoomBudgets());
        assertFalse(response.getBody().isOverallocated());
        assertNull(response.getBody().getWarningMessage());
        verify(budgetService, times(1)).validateBudget();
    }

    @Test
    public void testValidateBudgetWithOverallocation() {
        // Arrange
        BudgetValidationDto expectedValidation = createMockValidationDto(
            50000.0, 75000.0, true, "WARNING: Room budgets exceed total budget by 25000.00 PLN");
        when(budgetService.validateBudget()).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.validateBudget();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedValidation, response.getBody());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 25000.00 PLN",
                    response.getBody().getWarningMessage());
        verify(budgetService, times(1)).validateBudget();
    }

    @Test
    public void testUpdateBudget() {
        // Arrange
        Budget requestBudget = createMockBudget(120000.0);
        BudgetValidationDto expectedValidation = createMockValidationDto(
            120000.0, 75000.0, false, null);

        when(budgetService.updateBudgetLimit(120000.0)).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.updateBudget(requestBudget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedValidation, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(120000.0, response.getBody().getBudgetLimit());
        assertEquals(75000.0, response.getBody().getTotalRoomBudgets());
        assertFalse(response.getBody().isOverallocated());
        verify(budgetService, times(1)).updateBudgetLimit(120000.0);
    }

    @Test
    public void testUpdateBudgetWithZeroLimit() {
        // Arrange
        Budget requestBudget = createMockBudget(0.0);
        BudgetValidationDto expectedValidation = createMockValidationDto(
            0.0, 75000.0, true, "WARNING: Room budgets exceed total budget by 75000.00 PLN");

        when(budgetService.updateBudgetLimit(0.0)).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.updateBudget(requestBudget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedValidation, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(0.0, response.getBody().getBudgetLimit());
        assertTrue(response.getBody().isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 75000.00 PLN",
                    response.getBody().getWarningMessage());
        verify(budgetService, times(1)).updateBudgetLimit(0.0);
    }

    @Test
    public void testUpdateBudgetWithNegativeLimit() {
        // Arrange
        Budget requestBudget = createMockBudget(-10000.0);
        BudgetValidationDto expectedValidation = createMockValidationDto(
            -10000.0, 75000.0, true, "WARNING: Room budgets exceed total budget by 85000.00 PLN");

        when(budgetService.updateBudgetLimit(-10000.0)).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.updateBudget(requestBudget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedValidation, response.getBody());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isOverallocated());
        verify(budgetService, times(1)).updateBudgetLimit(-10000.0);
    }

    @Test
    public void testUpdateBudgetWithLargeLimit() {
        // Arrange
        Budget requestBudget = createMockBudget(1000000.0);
        BudgetValidationDto expectedValidation = createMockValidationDto(
            1000000.0, 75000.0, false, null);

        when(budgetService.updateBudgetLimit(1000000.0)).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.updateBudget(requestBudget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1000000.0, response.getBody().getBudgetLimit());
        assertEquals(925000.0, response.getBody().getAvailableBudget()); // 1000000 - 75000
        assertFalse(response.getBody().isOverallocated());
        verify(budgetService, times(1)).updateBudgetLimit(1000000.0);
    }

    @Test
    public void testUpdateBudgetExactlyAtLimit() {
        // Arrange - Budget exactly matches room budgets
        Budget requestBudget = createMockBudget(75000.0);
        BudgetValidationDto expectedValidation = createMockValidationDto(
            75000.0, 75000.0, false, null);

        when(budgetService.updateBudgetLimit(75000.0)).thenReturn(expectedValidation);

        // Act
        ResponseEntity<BudgetValidationDto> response = budgetController.updateBudget(requestBudget);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(75000.0, response.getBody().getBudgetLimit());
        assertEquals(0.0, response.getBody().getAvailableBudget());
        assertFalse(response.getBody().isOverallocated()); // Exactly at limit is OK
        assertNull(response.getBody().getWarningMessage());
        verify(budgetService, times(1)).updateBudgetLimit(75000.0);
    }
}
