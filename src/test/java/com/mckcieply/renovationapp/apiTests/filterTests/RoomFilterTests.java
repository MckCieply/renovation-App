package com.mckcieply.renovationapp.apiTests.filterTests;

import com.mckcieply.renovationapp.room.RoomFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomFilterTests {

    @Test
    public void testSettersAndGetters() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(1500L);
        filter.setMaxBudgetPlanned(7500L);

        // Assert
        assertEquals(1500L, filter.getMinBudgetPlanned());
        assertEquals(7500L, filter.getMaxBudgetPlanned());
    }

    @Test
    public void testConstructorDefault() {
        // Act
        RoomFilter filter = new RoomFilter();

        // Assert
        assertNull(filter.getMinBudgetPlanned());
        assertNull(filter.getMaxBudgetPlanned());
    }

    @Test
    public void testSettersWithNullValues() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(null);
        filter.setMaxBudgetPlanned(null);

        // Assert
        assertNull(filter.getMinBudgetPlanned());
        assertNull(filter.getMaxBudgetPlanned());
    }

    @Test
    public void testSettersWithZeroValues() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(0L);
        filter.setMaxBudgetPlanned(0L);

        // Assert
        assertEquals(0L, filter.getMinBudgetPlanned());
        assertEquals(0L, filter.getMaxBudgetPlanned());
    }

    @Test
    public void testSettersWithNegativeValues() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(-1000L);
        filter.setMaxBudgetPlanned(-500L);

        // Assert
        assertEquals(-1000L, filter.getMinBudgetPlanned());
        assertEquals(-500L, filter.getMaxBudgetPlanned());
    }

    @Test
    public void testSettersWithLargeValues() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(Long.MAX_VALUE - 1);
        filter.setMaxBudgetPlanned(Long.MAX_VALUE);

        // Assert
        assertEquals(Long.MAX_VALUE - 1, filter.getMinBudgetPlanned());
        assertEquals(Long.MAX_VALUE, filter.getMaxBudgetPlanned());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        RoomFilter filter1 = new RoomFilter();
        filter1.setMinBudgetPlanned(1000L);
        filter1.setMaxBudgetPlanned(5000L);

        RoomFilter filter2 = new RoomFilter();
        filter2.setMinBudgetPlanned(1000L);
        filter2.setMaxBudgetPlanned(5000L);

        RoomFilter filter3 = new RoomFilter();
        filter3.setMinBudgetPlanned(2000L);
        filter3.setMaxBudgetPlanned(5000L);

        // Assert
        assertEquals(filter1, filter2);
        assertEquals(filter1.hashCode(), filter2.hashCode());
        assertNotEquals(filter1, filter3);
        assertNotEquals(filter1.hashCode(), filter3.hashCode());
    }

    @Test
    public void testFilterWithInvalidRange() {
        // Arrange - Min budget greater than max budget
        RoomFilter filter = new RoomFilter();

        // Act
        filter.setMinBudgetPlanned(10000L);
        filter.setMaxBudgetPlanned(5000L);

        // Assert
        assertEquals(10000L, filter.getMinBudgetPlanned());
        assertEquals(5000L, filter.getMaxBudgetPlanned());
        // Note: Filter doesn't validate logic - that would be done at service level
    }

    @Test
    public void testFilterBoundaryValues() {
        // Arrange
        RoomFilter filter = new RoomFilter();

        // Act - Set same values for min and max
        filter.setMinBudgetPlanned(3000L);
        filter.setMaxBudgetPlanned(3000L);

        // Assert
        assertEquals(3000L, filter.getMinBudgetPlanned());
        assertEquals(3000L, filter.getMaxBudgetPlanned());
    }
}
