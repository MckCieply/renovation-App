package com.mckcieply.renovationapp.apiTests.filterTests;

import com.mckcieply.renovationapp.contractor.ContractorFilter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ContractorFilterTests {

    @Test
    public void testSettersAndGetters() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act
        filter.setFullName("John Doe");
        filter.setEmail("john@example.com");
        filter.setPhone("123456789");
        filter.setCreatedBy("admin");
        filter.setUpdatedBy("user");
        filter.setName("Test Contractor");
        filter.setFromCreatedAt(LocalDateTime.of(2024, 1, 1, 0, 0));
        filter.setToCreatedAt(LocalDateTime.of(2024, 12, 31, 23, 59));

        // Assert
        assertEquals("John Doe", filter.getFullName());
        assertEquals("john@example.com", filter.getEmail());
        assertEquals("123456789", filter.getPhone());
        assertEquals("admin", filter.getCreatedBy());
        assertEquals("user", filter.getUpdatedBy());
        assertEquals("Test Contractor", filter.getName());
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), filter.getFromCreatedAt());
        assertEquals(LocalDateTime.of(2024, 12, 31, 23, 59), filter.getToCreatedAt());
    }

    @Test
    public void testConstructorDefault() {
        // Act
        ContractorFilter filter = new ContractorFilter();

        // Assert
        assertNull(filter.getFullName());
        assertNull(filter.getEmail());
        assertNull(filter.getPhone());
        assertNull(filter.getCreatedBy());
        assertNull(filter.getUpdatedBy());
        assertNull(filter.getName());
        assertNull(filter.getFromCreatedAt());
        assertNull(filter.getToCreatedAt());
        assertNull(filter.getFromUpdatedAt());
        assertNull(filter.getToUpdatedAt());
    }

    @Test
    public void testSettersWithNullValues() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act
        filter.setFullName(null);
        filter.setEmail(null);
        filter.setPhone(null);
        filter.setCreatedBy(null);
        filter.setUpdatedBy(null);
        filter.setName(null);
        filter.setFromCreatedAt(null);
        filter.setToCreatedAt(null);

        // Assert
        assertNull(filter.getFullName());
        assertNull(filter.getEmail());
        assertNull(filter.getPhone());
        assertNull(filter.getCreatedBy());
        assertNull(filter.getUpdatedBy());
        assertNull(filter.getName());
        assertNull(filter.getFromCreatedAt());
        assertNull(filter.getToCreatedAt());
    }

    @Test
    public void testSettersWithEmptyStrings() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act
        filter.setFullName("");
        filter.setEmail("");
        filter.setPhone("");
        filter.setCreatedBy("");
        filter.setUpdatedBy("");
        filter.setName("");

        // Assert
        assertEquals("", filter.getFullName());
        assertEquals("", filter.getEmail());
        assertEquals("", filter.getPhone());
        assertEquals("", filter.getCreatedBy());
        assertEquals("", filter.getUpdatedBy());
        assertEquals("", filter.getName());
    }

    @Test
    public void testSettersWithSpecialCharacters() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act
        filter.setFullName("Józef Kowalski-Nowak");
        filter.setEmail("józef@test.com");
        filter.setPhone("+48 123 456 789");
        filter.setCreatedBy("admin@system");
        filter.setName("Firma & Co");

        // Assert
        assertEquals("Józef Kowalski-Nowak", filter.getFullName());
        assertEquals("józef@test.com", filter.getEmail());
        assertEquals("+48 123 456 789", filter.getPhone());
        assertEquals("admin@system", filter.getCreatedBy());
        assertEquals("Firma & Co", filter.getName());
    }

    @Test
    public void testSettersWithLongValues() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();
        String longName = "This is a very long contractor full name that exceeds normal length".repeat(3);
        String longEmail = "very.long.email.address.that.might.be.used.for.testing@example.com";
        String longPhone = "123456789012345678901234567890";

        // Act
        filter.setFullName(longName);
        filter.setEmail(longEmail);
        filter.setPhone(longPhone);

        // Assert
        assertEquals(longName, filter.getFullName());
        assertEquals(longEmail, filter.getEmail());
        assertEquals(longPhone, filter.getPhone());
    }

    @Test
    public void testDateRangeSettings() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 12, 31, 23, 59);

        // Act
        filter.setFromCreatedAt(from);
        filter.setToCreatedAt(to);
        filter.setFromUpdatedAt(from);
        filter.setToUpdatedAt(to);

        // Assert
        assertEquals(from, filter.getFromCreatedAt());
        assertEquals(to, filter.getToCreatedAt());
        assertEquals(from, filter.getFromUpdatedAt());
        assertEquals(to, filter.getToUpdatedAt());
    }

    @Test
    public void testInvalidDateRangeScenario() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();
        LocalDateTime from = LocalDateTime.of(2024, 12, 31, 23, 59);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 0, 0);

        // Act - Just set the values, validation happens in service layer
        filter.setFromCreatedAt(from);
        filter.setToCreatedAt(to);

        // Assert - Values are set regardless of logical validity
        assertEquals(from, filter.getFromCreatedAt());
        assertEquals(to, filter.getToCreatedAt());
    }

    @Test
    public void testNullDateValues() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act
        filter.setFromCreatedAt(null);
        filter.setToCreatedAt(null);
        filter.setFromUpdatedAt(null);
        filter.setToUpdatedAt(null);

        // Assert
        assertNull(filter.getFromCreatedAt());
        assertNull(filter.getToCreatedAt());
        assertNull(filter.getFromUpdatedAt());
        assertNull(filter.getToUpdatedAt());
    }

    @Test
    public void testSameDateValues() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();
        LocalDateTime sameDate = LocalDateTime.of(2024, 6, 15, 12, 0);

        // Act
        filter.setFromCreatedAt(sameDate);
        filter.setToCreatedAt(sameDate);

        // Assert
        assertEquals(sameDate, filter.getFromCreatedAt());
        assertEquals(sameDate, filter.getToCreatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        ContractorFilter filter1 = new ContractorFilter();
        filter1.setFullName("John Doe");
        filter1.setEmail("john@example.com");
        filter1.setPhone("123456789");

        ContractorFilter filter2 = new ContractorFilter();
        filter2.setFullName("John Doe");
        filter2.setEmail("john@example.com");
        filter2.setPhone("123456789");

        ContractorFilter filter3 = new ContractorFilter();
        filter3.setFullName("Jane Doe");
        filter3.setEmail("john@example.com");
        filter3.setPhone("123456789");

        // Assert
        assertEquals(filter1, filter2);
        assertEquals(filter1.hashCode(), filter2.hashCode());
        assertNotEquals(filter1, filter3);
        assertNotEquals(filter1.hashCode(), filter3.hashCode());
    }

    @Test
    public void testFilterWithPartialValues() {
        // Test filter with only some values set
        ContractorFilter filter1 = new ContractorFilter();
        filter1.setFullName("John Doe");
        // Other values remain null

        ContractorFilter filter2 = new ContractorFilter();
        filter2.setEmail("test@example.com");
        filter2.setPhone("123456789");
        // Other values remain null

        ContractorFilter filter3 = new ContractorFilter();
        filter3.setCreatedBy("admin");
        filter3.setName("Test Name");
        // Other values remain null

        assertEquals("John Doe", filter1.getFullName());
        assertNull(filter1.getEmail());
        assertNull(filter1.getPhone());

        assertNull(filter2.getFullName());
        assertEquals("test@example.com", filter2.getEmail());
        assertEquals("123456789", filter2.getPhone());

        assertNull(filter3.getFullName());
        assertEquals("admin", filter3.getCreatedBy());
        assertEquals("Test Name", filter3.getName());
    }

    @Test
    public void testFilterResetValues() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();
        filter.setFullName("John Doe");
        filter.setEmail("john@example.com");
        filter.setPhone("123456789");
        filter.setCreatedBy("admin");

        // Act - Reset all values
        filter.setFullName(null);
        filter.setEmail(null);
        filter.setPhone(null);
        filter.setCreatedBy(null);

        // Assert
        assertNull(filter.getFullName());
        assertNull(filter.getEmail());
        assertNull(filter.getPhone());
        assertNull(filter.getCreatedBy());
    }

    @Test
    public void testFilterMutability() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act - Set initial values
        filter.setFullName("Original Name");
        filter.setEmail("original@example.com");

        String originalName = filter.getFullName();
        String originalEmail = filter.getEmail();

        // Modify values
        filter.setFullName("Modified Name");
        filter.setEmail("modified@example.com");

        // Assert - Values can be modified
        assertNotEquals(originalName, filter.getFullName());
        assertNotEquals(originalEmail, filter.getEmail());
        assertEquals("Modified Name", filter.getFullName());
        assertEquals("modified@example.com", filter.getEmail());
    }

    @Test
    public void testComplexFilterScenario() {
        // Arrange
        ContractorFilter filter = new ContractorFilter();

        // Act - Set all possible values
        filter.setFullName("Józef Kowalski");
        filter.setEmail("jozef@test.pl");
        filter.setPhone("+48123456789");
        filter.setCreatedBy("system_admin");
        filter.setUpdatedBy("user_123");
        filter.setName("Kowalski Construction");
        filter.setFromCreatedAt(LocalDateTime.of(2024, 1, 1, 0, 0));
        filter.setToCreatedAt(LocalDateTime.of(2024, 6, 30, 23, 59));
        filter.setFromUpdatedAt(LocalDateTime.of(2024, 2, 1, 0, 0));
        filter.setToUpdatedAt(LocalDateTime.of(2024, 7, 31, 23, 59));

        // Assert - All values should be set correctly
        assertEquals("Józef Kowalski", filter.getFullName());
        assertEquals("jozef@test.pl", filter.getEmail());
        assertEquals("+48123456789", filter.getPhone());
        assertEquals("system_admin", filter.getCreatedBy());
        assertEquals("user_123", filter.getUpdatedBy());
        assertEquals("Kowalski Construction", filter.getName());
        assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), filter.getFromCreatedAt());
        assertEquals(LocalDateTime.of(2024, 6, 30, 23, 59), filter.getToCreatedAt());
        assertEquals(LocalDateTime.of(2024, 2, 1, 0, 0), filter.getFromUpdatedAt());
        assertEquals(LocalDateTime.of(2024, 7, 31, 23, 59), filter.getToUpdatedAt());
    }
}
