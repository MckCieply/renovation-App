package com.mckcieply.renovationapp.apiTests.entityTests;

import com.mckcieply.renovationapp.budget.Budget;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTests {

    @Test
    public void testBudgetCreation() {
        // Act
        Budget budget = new Budget();

        // Assert
        assertNotNull(budget);
        assertNull(budget.getId());
        assertEquals(0.0, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetSettersAndGetters() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setId(1L);
        budget.setBudgetLimit(100000.0);

        // Assert
        assertEquals(1L, budget.getId());
        assertEquals(100000.0, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetWithNegativeLimit() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setBudgetLimit(-1000.0);

        // Assert
        assertEquals(-1000.0, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetWithZeroLimit() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setBudgetLimit(0.0);

        // Assert
        assertEquals(0.0, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetWithLargeLimit() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setBudgetLimit(999999999.99);

        // Assert
        assertEquals(999999999.99, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetWithDecimalLimit() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setBudgetLimit(123456.789);

        // Assert
        assertEquals(123456.789, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetEquality() {
        // Arrange
        Budget budget1 = new Budget();
        budget1.setId(1L);
        budget1.setBudgetLimit(100000.0);

        Budget budget2 = new Budget();
        budget2.setId(1L);
        budget2.setBudgetLimit(100000.0);

        Budget budget3 = new Budget();
        budget3.setId(2L);
        budget3.setBudgetLimit(100000.0);

        // Assert
        assertEquals(budget1, budget2);
        assertNotEquals(budget1, budget3);
    }

    @Test
    public void testBudgetHashCode() {
        // Arrange
        Budget budget1 = new Budget();
        budget1.setId(1L);
        budget1.setBudgetLimit(100000.0);

        Budget budget2 = new Budget();
        budget2.setId(1L);
        budget2.setBudgetLimit(100000.0);

        // Assert
        assertEquals(budget1.hashCode(), budget2.hashCode());
    }

    @Test
    public void testBudgetToString() {
        // Arrange
        Budget budget = new Budget();
        budget.setId(1L);
        budget.setBudgetLimit(100000.0);

        // Act
        String result = budget.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Budget"));
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("budgetLimit=100000.0"));
    }

    @Test
    public void testBudgetWithNullId() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setId(null);
        budget.setBudgetLimit(50000.0);

        // Assert
        assertNull(budget.getId());
        assertEquals(50000.0, budget.getBudgetLimit());
    }

    @Test
    public void testBudgetImmutableAfterSet() {
        // Arrange
        Budget budget = new Budget();

        // Act
        budget.setId(1L);
        budget.setBudgetLimit(100000.0);

        Long originalId = budget.getId();
        Double originalLimit = budget.getBudgetLimit();

        // Modify values
        budget.setId(2L);
        budget.setBudgetLimit(200000.0);

        // Assert
        assertNotEquals(originalId, budget.getId());
        assertNotEquals(originalLimit, budget.getBudgetLimit());
        assertEquals(2L, budget.getId());
        assertEquals(200000.0, budget.getBudgetLimit());
    }
}
