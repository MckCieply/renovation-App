package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.renovationapp.budget.*;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.room.RoomRepository;
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
public class BudgetServiceTests {

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private BudgetService budgetService;

    private Budget createMockBudget(double limit) {
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setBudgetLimit(limit);
        return budget;
    }

    private Room createMockRoom(Long id, String name, Long budgetPlanned) {
        return Room.builder()
                .id(id)
                .name(name)
                .budgetPlanned(budgetPlanned)
                .build();
    }

    @Test
    public void testGetBudget() {
        // Arrange
        Budget expectedBudget = createMockBudget(100000.0);
        when(budgetRepository.findAll()).thenReturn(List.of(expectedBudget));

        // Act
        Budget result = budgetService.getBudget();

        // Assert
        assertEquals(expectedBudget, result);
        assertEquals(100000.0, result.getBudgetLimit());
        verify(budgetRepository, times(1)).findAll();
    }

    @Test
    public void testGetTotalRoomBudgets() {
        // Arrange
        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 25000L),
                createMockRoom(2L, "Kitchen", 30000L),
                createMockRoom(3L, "Bathroom", 20000L)
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        // Act
        double result = budgetService.getTotalRoomBudgets();

        // Assert
        assertEquals(75000.0, result);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testGetTotalRoomBudgetsWithNullValues() {
        // Arrange
        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 25000L),
                createMockRoom(2L, "Kitchen", null), // null budget
                createMockRoom(3L, "Bathroom", 20000L)
        );
        when(roomRepository.findAll()).thenReturn(rooms);

        // Act
        double result = budgetService.getTotalRoomBudgets();

        // Assert
        assertEquals(45000.0, result); // null is treated as 0
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testGetTotalRoomBudgetsEmptyList() {
        // Arrange
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        double result = budgetService.getTotalRoomBudgets();

        // Assert
        assertEquals(0.0, result);
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void testGetTotalEstimatedCosts() {
        // Arrange
        Double expectedTotal = 45000.0;
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(expectedTotal);

        // Act
        double result = budgetService.getTotalEstimatedCosts();

        // Assert
        assertEquals(45000.0, result);
        verify(roomRepository, times(1)).getTotalEstimatedCosts();
    }

    @Test
    public void testGetTotalEstimatedCostsWithNull() {
        // Arrange
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(null);

        // Act
        double result = budgetService.getTotalEstimatedCosts();

        // Assert
        assertEquals(0.0, result);
        verify(roomRepository, times(1)).getTotalEstimatedCosts();
    }

    @Test
    public void testGetTotalPaidCosts() {
        // Arrange
        Double expectedTotal = 25000.0;
        when(roomRepository.getTotalPaidCosts()).thenReturn(expectedTotal);

        // Act
        double result = budgetService.getTotalPaidCosts();

        // Assert
        assertEquals(25000.0, result);
        verify(roomRepository, times(1)).getTotalPaidCosts();
    }

    @Test
    public void testGetTotalPaidCostsWithNull() {
        // Arrange
        when(roomRepository.getTotalPaidCosts()).thenReturn(null);

        // Act
        double result = budgetService.getTotalPaidCosts();

        // Assert
        assertEquals(0.0, result);
        verify(roomRepository, times(1)).getTotalPaidCosts();
    }

    @Test
    public void testValidateBudgetWithinLimit() {
        // Arrange
        Budget budget = createMockBudget(100000.0);
        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 25000L),
                createMockRoom(2L, "Kitchen", 30000L)
        );

        when(budgetRepository.findAll()).thenReturn(List.of(budget));
        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(45000.0);
        when(roomRepository.getTotalPaidCosts()).thenReturn(20000.0);

        // Act
        BudgetValidationDto result = budgetService.validateBudget();

        // Assert
        assertEquals(100000.0, result.getBudgetLimit());
        assertEquals(55000.0, result.getTotalRoomBudgets());
        assertEquals(45000.0, result.getTotalEstimatedCosts());
        assertEquals(20000.0, result.getTotalPaidCosts());
        assertEquals(45000.0, result.getAvailableBudget()); // 100000 - 55000
        assertFalse(result.isOverallocated());
        assertNull(result.getWarningMessage());
    }

    @Test
    public void testValidateBudgetOverallocated() {
        // Arrange
        Budget budget = createMockBudget(50000.0);
        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 35000L),
                createMockRoom(2L, "Kitchen", 25000L)
        );

        when(budgetRepository.findAll()).thenReturn(List.of(budget));
        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(45000.0);
        when(roomRepository.getTotalPaidCosts()).thenReturn(20000.0);

        // Act
        BudgetValidationDto result = budgetService.validateBudget();

        // Assert
        assertEquals(50000.0, result.getBudgetLimit());
        assertEquals(60000.0, result.getTotalRoomBudgets());
        assertEquals(-10000.0, result.getAvailableBudget()); // 50000 - 60000
        assertTrue(result.isOverallocated());
        assertEquals("WARNING: Room budgets exceed total budget by 10000.00 PLN", result.getWarningMessage());
    }

    @Test
    public void testUpdateBudgetLimit() {
        // Arrange
        Budget existingBudget = createMockBudget(50000.0);
        double newLimit = 120000.0;

        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 25000L),
                createMockRoom(2L, "Kitchen", 30000L)
        );

        when(budgetRepository.findAll()).thenReturn(List.of(existingBudget));
        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(45000.0);
        when(roomRepository.getTotalPaidCosts()).thenReturn(20000.0);
        when(budgetRepository.save(any(Budget.class))).thenReturn(existingBudget);

        // Act
        BudgetValidationDto result = budgetService.updateBudgetLimit(newLimit);

        // Assert
        assertEquals(120000.0, existingBudget.getBudgetLimit()); // Budget was updated
        assertEquals(120000.0, result.getBudgetLimit());
        assertEquals(55000.0, result.getTotalRoomBudgets());
        assertEquals(65000.0, result.getAvailableBudget()); // 120000 - 55000
        assertFalse(result.isOverallocated());
        assertNull(result.getWarningMessage());

        verify(budgetRepository, times(1)).save(existingBudget);
        verify(budgetRepository, times(2)).findAll(); // Once in updateBudgetLimit, once in validateBudget
    }

    @Test
    public void testBudgetInitWhenBudgetExists() {
        // Arrange
        Budget existingBudget = createMockBudget(100000.0);
        when(budgetRepository.findAll()).thenReturn(List.of(existingBudget));

        // Act
        budgetService.budgetInit();

        // Assert
        verify(budgetRepository, times(1)).findAll();
        verify(budgetRepository, never()).save(any()); // Should not save if budget exists
    }

    @Test
    public void testBudgetInitWhenNoBudgetExists() {
        // Arrange
        when(budgetRepository.findAll()).thenReturn(Collections.emptyList());
        when(budgetRepository.save(any(Budget.class))).thenAnswer(invocation -> {
            Budget saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act
        budgetService.budgetInit();

        // Assert
        verify(budgetRepository, times(1)).findAll();
        verify(budgetRepository, times(1)).save(argThat(budget ->
            budget.getBudgetLimit() == 0.0
        ));
    }

    @Test
    public void testValidateBudgetEdgeCase() {
        // Arrange - Exactly at budget limit
        Budget budget = createMockBudget(75000.0);
        List<Room> rooms = Arrays.asList(
                createMockRoom(1L, "Living Room", 35000L),
                createMockRoom(2L, "Kitchen", 40000L)
        );

        when(budgetRepository.findAll()).thenReturn(List.of(budget));
        when(roomRepository.findAll()).thenReturn(rooms);
        when(roomRepository.getTotalEstimatedCosts()).thenReturn(65000.0);
        when(roomRepository.getTotalPaidCosts()).thenReturn(30000.0);

        // Act
        BudgetValidationDto result = budgetService.validateBudget();

        // Assert
        assertEquals(75000.0, result.getBudgetLimit());
        assertEquals(75000.0, result.getTotalRoomBudgets());
        assertEquals(0.0, result.getAvailableBudget()); // Exactly at limit
        assertFalse(result.isOverallocated()); // Exactly at limit is not overallocated
        assertNull(result.getWarningMessage());
    }
}
