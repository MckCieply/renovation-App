package com.mckcieply.renovationapp.apiTests.entityTests;

import com.mckcieply.renovationapp.contractor.Contractor;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.work.Work;
import com.mckcieply.renovationapp.workType.WorkType;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class WorkTests {

    private final Room room = mock(Room.class);
    private final WorkType workType = mock(WorkType.class);
    private final Contractor contractor = mock(Contractor.class);

    @Test
    public void testWorkCreationWithBuilder() {
        // Act
        Work work = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .estMaterialCost(1000.0)
                .estLaborCost(500.0)
                .finalMaterialCost(1200.0)
                .finalLaborCost(600.0)
                .state(EnumWorkState.IN_PROGRESS)
                .paid(false)
                .startDate(Date.valueOf("2024-01-01"))
                .endDate(Date.valueOf("2024-01-31"))
                .room(room)
                .workType(workType)
                .contractor(contractor)
                .build();

        // Assert
        assertNotNull(work);
        assertEquals(1L, work.getId());
        assertEquals("Test Work", work.getName());
        assertEquals("Test Description", work.getDescription());
        assertEquals(1000.0, work.getEstMaterialCost());
        assertEquals(500.0, work.getEstLaborCost());
        assertEquals(1200.0, work.getFinalMaterialCost());
        assertEquals(600.0, work.getFinalLaborCost());
        assertEquals(EnumWorkState.IN_PROGRESS, work.getState());
        assertFalse(work.isPaid());
        assertEquals(Date.valueOf("2024-01-01"), work.getStartDate());
        assertEquals(Date.valueOf("2024-01-31"), work.getEndDate());
        assertEquals(room, work.getRoom());
        assertEquals(workType, work.getWorkType());
        assertEquals(contractor, work.getContractor());
    }

    @Test
    public void testWorkCreationNoArgsConstructor() {
        // Act
        Work work = new Work();

        // Assert
        assertNotNull(work);
        assertNull(work.getId());
        assertNull(work.getName());
        assertNull(work.getDescription());
        assertEquals(0.0, work.getEstMaterialCost());
        assertEquals(0.0, work.getEstLaborCost());
        assertEquals(0.0, work.getFinalMaterialCost());
        assertEquals(0.0, work.getFinalLaborCost());
        assertNull(work.getState());
        assertFalse(work.isPaid());
        assertNull(work.getStartDate());
        assertNull(work.getEndDate());
        assertNull(work.getRoom());
        assertNull(work.getWorkType());
        assertNull(work.getContractor());
    }

    @Test
    public void testWorkSettersAndGetters() {
        // Arrange
        Work work = new Work();

        // Act
        work.setId(2L);
        work.setName("Updated Work");
        work.setDescription("Updated Description");
        work.setEstMaterialCost(2000.0);
        work.setEstLaborCost(1000.0);
        work.setFinalMaterialCost(2500.0);
        work.setFinalLaborCost(1200.0);
        work.setState(EnumWorkState.FINISHED);
        work.setPaid(true);
        work.setStartDate(Date.valueOf("2024-02-01"));
        work.setEndDate(Date.valueOf("2024-02-28"));
        work.setRoom(room);
        work.setWorkType(workType);
        work.setContractor(contractor);

        // Assert
        assertEquals(2L, work.getId());
        assertEquals("Updated Work", work.getName());
        assertEquals("Updated Description", work.getDescription());
        assertEquals(2000.0, work.getEstMaterialCost());
        assertEquals(1000.0, work.getEstLaborCost());
        assertEquals(2500.0, work.getFinalMaterialCost());
        assertEquals(1200.0, work.getFinalLaborCost());
        assertEquals(EnumWorkState.FINISHED, work.getState());
        assertTrue(work.isPaid());
        assertEquals(Date.valueOf("2024-02-01"), work.getStartDate());
        assertEquals(Date.valueOf("2024-02-28"), work.getEndDate());
        assertEquals(room, work.getRoom());
        assertEquals(workType, work.getWorkType());
        assertEquals(contractor, work.getContractor());
    }

    @Test
    public void testGetFinalCosts() {
        // Arrange
        Work work = Work.builder()
                .finalMaterialCost(1500.0)
                .finalLaborCost(800.0)
                .build();

        // Act
        double finalCosts = work.getFinalCosts();

        // Assert
        assertEquals(2300.0, finalCosts);
    }

    @Test
    public void testGetEstimatedCosts() {
        // Arrange
        Work work = Work.builder()
                .estMaterialCost(1200.0)
                .estLaborCost(700.0)
                .build();

        // Act
        double estimatedCosts = work.getEstimatedCosts();

        // Assert
        assertEquals(1900.0, estimatedCosts);
    }

    @Test
    public void testGetFinalCostsWithZeroValues() {
        // Arrange
        Work work = Work.builder()
                .finalMaterialCost(0.0)
                .finalLaborCost(0.0)
                .build();

        // Act
        double finalCosts = work.getFinalCosts();

        // Assert
        assertEquals(0.0, finalCosts);
    }

    @Test
    public void testGetEstimatedCostsWithZeroValues() {
        // Arrange
        Work work = Work.builder()
                .estMaterialCost(0.0)
                .estLaborCost(0.0)
                .build();

        // Act
        double estimatedCosts = work.getEstimatedCosts();

        // Assert
        assertEquals(0.0, estimatedCosts);
    }

    @Test
    public void testWorkWithAllEnumStates() {
        // Test all possible work states
        Work plannedWork = Work.builder().state(EnumWorkState.PLANNED).build();
        Work inProgressWork = Work.builder().state(EnumWorkState.IN_PROGRESS).build();
        Work finishedWork = Work.builder().state(EnumWorkState.FINISHED).build();

        assertEquals(EnumWorkState.PLANNED, plannedWork.getState());
        assertEquals(EnumWorkState.IN_PROGRESS, inProgressWork.getState());
        assertEquals(EnumWorkState.FINISHED, finishedWork.getState());
    }

    @Test
    public void testWorkWithNegativeCosts() {
        // Arrange & Act
        Work work = Work.builder()
                .estMaterialCost(-100.0)
                .estLaborCost(-50.0)
                .finalMaterialCost(-120.0)
                .finalLaborCost(-60.0)
                .build();

        // Assert
        assertEquals(-100.0, work.getEstMaterialCost());
        assertEquals(-50.0, work.getEstLaborCost());
        assertEquals(-120.0, work.getFinalMaterialCost());
        assertEquals(-60.0, work.getFinalLaborCost());
        assertEquals(-150.0, work.getEstimatedCosts());
        assertEquals(-180.0, work.getFinalCosts());
    }

    @Test
    public void testWorkWithLargeCosts() {
        // Arrange & Act
        Work work = Work.builder()
                .estMaterialCost(Double.MAX_VALUE / 2)
                .estLaborCost(Double.MAX_VALUE / 2)
                .finalMaterialCost(Double.MAX_VALUE / 3)
                .finalLaborCost(Double.MAX_VALUE / 3)
                .build();

        // Assert
        assertEquals(Double.MAX_VALUE / 2, work.getEstMaterialCost());
        assertEquals(Double.MAX_VALUE / 2, work.getEstLaborCost());
        assertEquals(Double.MAX_VALUE, work.getEstimatedCosts());
    }

    @Test
    public void testWorkEquality() {
        // Arrange
        Work work1 = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .estMaterialCost(1000.0)
                .build();

        Work work2 = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .estMaterialCost(1000.0)
                .build();

        Work work3 = Work.builder()
                .id(2L)
                .name("Test Work")
                .description("Test Description")
                .estMaterialCost(1000.0)
                .build();

        // Assert
        assertEquals(work1, work2);
        assertNotEquals(work1, work3);
    }

    @Test
    public void testWorkHashCode() {
        // Arrange
        Work work1 = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .build();

        Work work2 = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .build();

        // Assert
        assertEquals(work1.hashCode(), work2.hashCode());
    }

    @Test
    public void testWorkToString() {
        // Arrange
        Work work = Work.builder()
                .id(1L)
                .name("Test Work")
                .description("Test Description")
                .estMaterialCost(1000.0)
                .build();

        // Act
        String result = work.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testWorkWithNullDates() {
        // Arrange & Act
        Work work = Work.builder()
                .startDate(null)
                .endDate(null)
                .build();

        // Assert
        assertNull(work.getStartDate());
        assertNull(work.getEndDate());
    }

    @Test
    public void testWorkWithSameDates() {
        // Arrange
        Date sameDate = Date.valueOf("2024-01-01");

        // Act
        Work work = Work.builder()
                .startDate(sameDate)
                .endDate(sameDate)
                .build();

        // Assert
        assertEquals(sameDate, work.getStartDate());
        assertEquals(sameDate, work.getEndDate());
    }

    @Test
    public void testWorkInheritanceFromBaseEntity() {
        // Arrange & Act
        Work work = Work.builder()
                .id(1L)
                .name("Test Work")
                .build();

        // Assert
        assertNotNull(work);
        // Work extends BaseEntity, so it should inherit base properties
        assertNotNull(work.getId());
        assertNotNull(work.getName());
    }

    @Test
    public void testWorkBuilderPattern() {
        // Act
        Work work = Work.builder()
                .id(10L)
                .name("Builder Test Work")
                .description("Testing builder pattern")
                .estMaterialCost(1234.56)
                .estLaborCost(987.65)
                .state(EnumWorkState.PLANNED)
                .paid(false)
                .build();

        // Assert
        assertEquals(10L, work.getId());
        assertEquals("Builder Test Work", work.getName());
        assertEquals("Testing builder pattern", work.getDescription());
        assertEquals(1234.56, work.getEstMaterialCost());
        assertEquals(987.65, work.getEstLaborCost());
        assertEquals(EnumWorkState.PLANNED, work.getState());
        assertFalse(work.isPaid());
        assertEquals(2222.21, work.getEstimatedCosts(), 0.01);
    }

    @Test
    public void testWorkWithDecimalPrecision() {
        // Arrange & Act
        Work work = Work.builder()
                .estMaterialCost(1234.567890)
                .estLaborCost(987.654321)
                .finalMaterialCost(1300.123456)
                .finalLaborCost(1050.987654)
                .build();

        // Assert
        assertEquals(1234.567890, work.getEstMaterialCost(), 0.000001);
        assertEquals(987.654321, work.getEstLaborCost(), 0.000001);
        assertEquals(1300.123456, work.getFinalMaterialCost(), 0.000001);
        assertEquals(1050.987654, work.getFinalLaborCost(), 0.000001);
        assertEquals(2222.222211, work.getEstimatedCosts(), 0.000001);
        assertEquals(2351.11111, work.getFinalCosts(), 0.000001);
    }
}
