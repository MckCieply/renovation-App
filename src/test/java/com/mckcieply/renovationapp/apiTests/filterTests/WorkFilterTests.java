package com.mckcieply.renovationapp.apiTests.filterTests;

import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.work.WorkFilter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkFilterTests {

    @Test
    public void testSettersAndGetters() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setState(EnumWorkState.IN_PROGRESS);
        filter.setPaid(true);
        filter.setRoomId(1L);
        filter.setWorkTypeId(2L);
        filter.setDescription("Test description");

        // Assert
        assertEquals(EnumWorkState.IN_PROGRESS, filter.getState());
        assertTrue(filter.getPaid());
        assertEquals(1L, filter.getRoomId());
        assertEquals(2L, filter.getWorkTypeId());
        assertEquals("Test description", filter.getDescription());
    }

    @Test
    public void testConstructorDefault() {
        // Act
        WorkFilter filter = new WorkFilter();

        // Assert
        assertNull(filter.getState());
        assertNull(filter.getPaid());
        assertNull(filter.getRoomId());
        assertNull(filter.getWorkTypeId());
        assertNull(filter.getDescription());
    }

    @Test
    public void testSettersWithNullValues() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setState(null);
        filter.setPaid(null);
        filter.setRoomId(null);
        filter.setWorkTypeId(null);
        filter.setDescription(null);

        // Assert
        assertNull(filter.getState());
        assertNull(filter.getPaid());
        assertNull(filter.getRoomId());
        assertNull(filter.getWorkTypeId());
        assertNull(filter.getDescription());
    }

    @Test
    public void testSettersWithAllEnumStates() {
        // Test all possible work states
        WorkFilter plannedFilter = new WorkFilter();
        WorkFilter inProgressFilter = new WorkFilter();
        WorkFilter finishedFilter = new WorkFilter();

        plannedFilter.setState(EnumWorkState.PLANNED);
        inProgressFilter.setState(EnumWorkState.IN_PROGRESS);
        finishedFilter.setState(EnumWorkState.FINISHED);

        assertEquals(EnumWorkState.PLANNED, plannedFilter.getState());
        assertEquals(EnumWorkState.IN_PROGRESS, inProgressFilter.getState());
        assertEquals(EnumWorkState.FINISHED, finishedFilter.getState());
    }

    @Test
    public void testSettersWithBooleanValues() {
        // Arrange
        WorkFilter paidFilter = new WorkFilter();
        WorkFilter unpaidFilter = new WorkFilter();

        // Act
        paidFilter.setPaid(true);
        unpaidFilter.setPaid(false);

        // Assert
        assertTrue(paidFilter.getPaid());
        assertFalse(unpaidFilter.getPaid());
    }

    @Test
    public void testSettersWithZeroValues() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setRoomId(0L);
        filter.setWorkTypeId(0L);

        // Assert
        assertEquals(0L, filter.getRoomId());
        assertEquals(0L, filter.getWorkTypeId());
    }

    @Test
    public void testSettersWithNegativeValues() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setRoomId(-1L);
        filter.setWorkTypeId(-2L);

        // Assert
        assertEquals(-1L, filter.getRoomId());
        assertEquals(-2L, filter.getWorkTypeId());
    }

    @Test
    public void testSettersWithLargeValues() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setRoomId(Long.MAX_VALUE);
        filter.setWorkTypeId(Long.MAX_VALUE - 1);

        // Assert
        assertEquals(Long.MAX_VALUE, filter.getRoomId());
        assertEquals(Long.MAX_VALUE - 1, filter.getWorkTypeId());
    }

    @Test
    public void testSettersWithEmptyDescription() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act
        filter.setDescription("");

        // Assert
        assertEquals("", filter.getDescription());
    }

    @Test
    public void testSettersWithLongDescription() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        String longDescription = "This is a very long description that exceeds normal length expectations for testing purposes. ".repeat(10);

        // Act
        filter.setDescription(longDescription);

        // Assert
        assertEquals(longDescription, filter.getDescription());
    }

    @Test
    public void testSettersWithSpecialCharactersInDescription() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        String specialDescription = "Test!@#$%^&*()_+-=[]{}|;':\",./<>?`~äöüß";

        // Act
        filter.setDescription(specialDescription);

        // Assert
        assertEquals(specialDescription, filter.getDescription());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        WorkFilter filter1 = new WorkFilter();
        filter1.setState(EnumWorkState.IN_PROGRESS);
        filter1.setPaid(true);
        filter1.setRoomId(1L);
        filter1.setWorkTypeId(2L);
        filter1.setDescription("Test");

        WorkFilter filter2 = new WorkFilter();
        filter2.setState(EnumWorkState.IN_PROGRESS);
        filter2.setPaid(true);
        filter2.setRoomId(1L);
        filter2.setWorkTypeId(2L);
        filter2.setDescription("Test");

        WorkFilter filter3 = new WorkFilter();
        filter3.setState(EnumWorkState.FINISHED);
        filter3.setPaid(true);
        filter3.setRoomId(1L);
        filter3.setWorkTypeId(2L);
        filter3.setDescription("Test");

        // Assert
        assertEquals(filter1, filter2);
        assertEquals(filter1.hashCode(), filter2.hashCode());
        assertNotEquals(filter1, filter3);
        assertNotEquals(filter1.hashCode(), filter3.hashCode());
    }

    @Test
    public void testFilterWithPartialValues() {
        // Test filter with only some values set
        WorkFilter filter1 = new WorkFilter();
        filter1.setState(EnumWorkState.PLANNED);
        // Other values remain null

        WorkFilter filter2 = new WorkFilter();
        filter2.setPaid(false);
        filter2.setRoomId(5L);
        // Other values remain null

        WorkFilter filter3 = new WorkFilter();
        filter3.setDescription("Partial test");
        // Other values remain null

        assertEquals(EnumWorkState.PLANNED, filter1.getState());
        assertNull(filter1.getPaid());
        assertNull(filter1.getRoomId());

        assertNull(filter2.getState());
        assertFalse(filter2.getPaid());
        assertEquals(5L, filter2.getRoomId());

        assertNull(filter3.getState());
        assertNull(filter3.getRoomId());
        assertEquals("Partial test", filter3.getDescription());
    }

    @Test
    public void testFilterResetValues() {
        // Arrange
        WorkFilter filter = new WorkFilter();
        filter.setState(EnumWorkState.IN_PROGRESS);
        filter.setPaid(true);
        filter.setRoomId(1L);
        filter.setWorkTypeId(2L);
        filter.setDescription("Test");

        // Act - Reset all values
        filter.setState(null);
        filter.setPaid(null);
        filter.setRoomId(null);
        filter.setWorkTypeId(null);
        filter.setDescription(null);

        // Assert
        assertNull(filter.getState());
        assertNull(filter.getPaid());
        assertNull(filter.getRoomId());
        assertNull(filter.getWorkTypeId());
        assertNull(filter.getDescription());
    }

    @Test
    public void testFilterMutability() {
        // Arrange
        WorkFilter filter = new WorkFilter();

        // Act - Set initial values
        filter.setState(EnumWorkState.PLANNED);
        filter.setPaid(false);

        EnumWorkState originalState = filter.getState();
        Boolean originalPaid = filter.getPaid();

        // Modify values
        filter.setState(EnumWorkState.FINISHED);
        filter.setPaid(true);

        // Assert - Values can be modified
        assertNotEquals(originalState, filter.getState());
        assertNotEquals(originalPaid, filter.getPaid());
        assertEquals(EnumWorkState.FINISHED, filter.getState());
        assertTrue(filter.getPaid());
    }
}
