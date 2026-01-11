package com.mckcieply.renovationapp.apiTests.entityTests;

import com.mckcieply.renovationapp.room.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTests {

    @Test
    public void testRoomCreationWithBuilder() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        // Assert
        assertNotNull(room);
        assertEquals(1L, room.getId());
        assertEquals("Living Room", room.getName());
        assertEquals(15000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomCreationNoArgsConstructor() {
        // Act
        Room room = new Room();

        // Assert
        assertNotNull(room);
        assertNull(room.getId());
        assertNull(room.getName());
        assertNull(room.getBudgetPlanned());
    }

    @Test
    public void testRoomSettersAndGetters() {
        // Arrange
        Room room = new Room();

        // Act
        room.setId(2L);
        room.setName("Kitchen");
        room.setBudgetPlanned(25000L);

        // Assert
        assertEquals(2L, room.getId());
        assertEquals("Kitchen", room.getName());
        assertEquals(25000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithNullBudget() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Bedroom")
                .budgetPlanned(null)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("Bedroom", room.getName());
        assertNull(room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithZeroBudget() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Storage")
                .budgetPlanned(0L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("Storage", room.getName());
        assertEquals(0L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithLargeBudget() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Luxury Room")
                .budgetPlanned(Long.MAX_VALUE)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("Luxury Room", room.getName());
        assertEquals(Long.MAX_VALUE, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithNegativeBudget() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Deficit Room")
                .budgetPlanned(-1000L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("Deficit Room", room.getName());
        assertEquals(-1000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomEquality() {
        // Arrange
        Room room1 = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        Room room2 = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        Room room3 = Room.builder()
                .id(2L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        // Assert
        assertEquals(room1, room2);
        assertNotEquals(room1, room3);
    }

    @Test
    public void testRoomHashCode() {
        // Arrange
        Room room1 = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        Room room2 = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        // Assert
        assertEquals(room1.hashCode(), room2.hashCode());
    }

    @Test
    public void testRoomToString() {
        // Arrange
        Room room = Room.builder()
                .id(1L)
                .name("Living Room")
                .budgetPlanned(15000L)
                .build();

        // Act
        String result = room.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // toString() should contain some representation of the Room object
    }

    @Test
    public void testRoomWithEmptyName() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("")
                .budgetPlanned(5000L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("", room.getName());
        assertEquals(5000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithLongName() {
        // Arrange
        String longName = "This is a very long room name that exceeds normal length expectations for testing purposes";

        // Act
        Room room = Room.builder()
                .id(1L)
                .name(longName)
                .budgetPlanned(10000L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals(longName, room.getName());
        assertEquals(10000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithSpecialCharactersInName() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name("Room #1 - Living & Dining Area (Main Floor)")
                .budgetPlanned(20000L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertEquals("Room #1 - Living & Dining Area (Main Floor)", room.getName());
        assertEquals(20000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomInheritanceFromBaseEntity() {
        // Arrange & Act
        Room room = Room.builder()
                .id(1L)
                .name("Test Room")
                .budgetPlanned(5000L)
                .build();

        // Assert
        assertNotNull(room);
        // Room extends BaseEntity, so it should inherit base properties
        assertNotNull(room.getId());
        assertNotNull(room.getName());
    }

    @Test
    public void testRoomBuilderPattern() {
        // Act
        Room room = Room.builder()
                .id(10L)
                .name("Builder Test Room")
                .budgetPlanned(12345L)
                .build();

        // Assert
        assertEquals(10L, room.getId());
        assertEquals("Builder Test Room", room.getName());
        assertEquals(12345L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomImmutabilityAfterCreation() {
        // Arrange
        Room room = Room.builder()
                .id(1L)
                .name("Original Room")
                .budgetPlanned(10000L)
                .build();

        // Act - Modify the room
        room.setName("Modified Room");
        room.setBudgetPlanned(20000L);

        // Assert - Verify the room can be modified (it's not immutable)
        assertEquals("Modified Room", room.getName());
        assertEquals(20000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithNullId() {
        // Act
        Room room = Room.builder()
                .id(null)
                .name("No ID Room")
                .budgetPlanned(5000L)
                .build();

        // Assert
        assertNull(room.getId());
        assertEquals("No ID Room", room.getName());
        assertEquals(5000L, room.getBudgetPlanned());
    }

    @Test
    public void testRoomWithNullName() {
        // Act
        Room room = Room.builder()
                .id(1L)
                .name(null)
                .budgetPlanned(5000L)
                .build();

        // Assert
        assertEquals(1L, room.getId());
        assertNull(room.getName());
        assertEquals(5000L, room.getBudgetPlanned());
    }
}
