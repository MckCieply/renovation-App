package com.mckcieply.renovationapp.apiTests.entityTests;

import com.mckcieply.renovationapp.contractor.Contractor;
import com.mckcieply.renovationapp.enumerable.EnumContractorType;
import com.mckcieply.renovationapp.workType.WorkType;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ContractorTests {

    private final WorkType workType1 = mock(WorkType.class);
    private final WorkType workType2 = mock(WorkType.class);

    @Test
    public void testContractorCreationWithBuilder() {
        // Arrange
        Set<WorkType> workTypes = new HashSet<>();
        workTypes.add(workType1);
        workTypes.add(workType2);

        // Act
        Contractor contractor = Contractor.builder()
                .id(1L)
                .name("John Doe Contractor")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .type(EnumContractorType.PRIVATE)
                .companyName("Doe Construction")
                .nip("1234567890")
                .regon("123456789")
                .address("123 Main St")
                .city("Warsaw")
                .postalCode("00-001")
                .country("Poland")
                .bankAccount("12 3456 7890 1234 5678 9012 3456")
                .description("Experienced contractor")
                .workTypes(workTypes)
                .build();

        // Assert
        assertNotNull(contractor);
        assertEquals(1L, contractor.getId());
        assertEquals("John Doe Contractor", contractor.getName());
        assertEquals("John", contractor.getFirstName());
        assertEquals("Doe", contractor.getLastName());
        assertEquals("john.doe@example.com", contractor.getEmail());
        assertEquals("123456789", contractor.getPhone());
        assertEquals(EnumContractorType.PRIVATE, contractor.getType());
        assertEquals("Doe Construction", contractor.getCompanyName());
        assertEquals("1234567890", contractor.getNip());
        assertEquals("123456789", contractor.getRegon());
        assertEquals("123 Main St", contractor.getAddress());
        assertEquals("Warsaw", contractor.getCity());
        assertEquals("00-001", contractor.getPostalCode());
        assertEquals("Poland", contractor.getCountry());
        assertEquals("12 3456 7890 1234 5678 9012 3456", contractor.getBankAccount());
        assertEquals("Experienced contractor", contractor.getDescription());
        assertEquals(2, contractor.getWorkTypes().size());
        assertTrue(contractor.getWorkTypes().contains(workType1));
        assertTrue(contractor.getWorkTypes().contains(workType2));
    }

    @Test
    public void testContractorCreationNoArgsConstructor() {
        // Act
        Contractor contractor = new Contractor();

        // Assert
        assertNotNull(contractor);
        assertNull(contractor.getId());
        assertNull(contractor.getName());
        assertNull(contractor.getFirstName());
        assertNull(contractor.getLastName());
        assertNull(contractor.getEmail());
        assertNull(contractor.getPhone());
        assertNull(contractor.getType());
        assertNull(contractor.getCompanyName());
        assertNull(contractor.getNip());
        assertNull(contractor.getRegon());
        assertNull(contractor.getAddress());
        assertNull(contractor.getCity());
        assertNull(contractor.getPostalCode());
        assertNull(contractor.getCountry());
        assertNull(contractor.getBankAccount());
        assertNull(contractor.getDescription());
        assertNotNull(contractor.getWorkTypes());
        assertTrue(contractor.getWorkTypes().isEmpty());
    }

    @Test
    public void testContractorSettersAndGetters() {
        // Arrange
        Contractor contractor = new Contractor();
        Set<WorkType> workTypes = new HashSet<>();
        workTypes.add(workType1);

        // Act
        contractor.setId(2L);
        contractor.setName("Jane Smith Contractor");
        contractor.setFirstName("Jane");
        contractor.setLastName("Smith");
        contractor.setFullName("Jane Smith");
        contractor.setEmail("jane.smith@example.com");
        contractor.setPhone("987654321");
        contractor.setType(EnumContractorType.COMPANY);
        contractor.setCompanyName("Smith & Co");
        contractor.setNip("9876543210");
        contractor.setRegon("987654321");
        contractor.setAddress("456 Oak Ave");
        contractor.setCity("Krakow");
        contractor.setPostalCode("30-001");
        contractor.setCountry("Poland");
        contractor.setBankAccount("98 7654 3210 9876 5432 1098 7654");
        contractor.setDescription("Professional contractor");
        contractor.setWorkTypes(workTypes);

        // Assert
        assertEquals(2L, contractor.getId());
        assertEquals("Jane Smith Contractor", contractor.getName());
        assertEquals("Jane", contractor.getFirstName());
        assertEquals("Smith", contractor.getLastName());
        assertEquals("Jane Smith", contractor.getFullName());
        assertEquals("jane.smith@example.com", contractor.getEmail());
        assertEquals("987654321", contractor.getPhone());
        assertEquals(EnumContractorType.COMPANY, contractor.getType());
        assertEquals("Smith & Co", contractor.getCompanyName());
        assertEquals("9876543210", contractor.getNip());
        assertEquals("987654321", contractor.getRegon());
        assertEquals("456 Oak Ave", contractor.getAddress());
        assertEquals("Krakow", contractor.getCity());
        assertEquals("30-001", contractor.getPostalCode());
        assertEquals("Poland", contractor.getCountry());
        assertEquals("98 7654 3210 9876 5432 1098 7654", contractor.getBankAccount());
        assertEquals("Professional contractor", contractor.getDescription());
        assertEquals(1, contractor.getWorkTypes().size());
        assertTrue(contractor.getWorkTypes().contains(workType1));
    }

    @Test
    public void testContractorWithAllEnumTypes() {
        // Test all possible contractor types
        Contractor privateContractor = Contractor.builder().type(EnumContractorType.PRIVATE).build();
        Contractor companyContractor = Contractor.builder().type(EnumContractorType.COMPANY).build();

        assertEquals(EnumContractorType.PRIVATE, privateContractor.getType());
        assertEquals(EnumContractorType.COMPANY, companyContractor.getType());
    }

    @Test
    public void testContractorWithMinimalRequiredFields() {
        // Act
        Contractor contractor = Contractor.builder()
                .firstName("Min")
                .lastName("Max")
                .email("min@example.com")
                .phone("12345678")
                .build();

        // Assert
        assertEquals("Min", contractor.getFirstName());
        assertEquals("Max", contractor.getLastName());
        assertEquals("min@example.com", contractor.getEmail());
        assertEquals("12345678", contractor.getPhone());
    }

    @Test
    public void testContractorWithEmptyWorkTypes() {
        // Act
        Contractor contractor = Contractor.builder()
                .firstName("Test")
                .lastName("User")
                .workTypes(new HashSet<>())
                .build();

        // Assert
        assertNotNull(contractor.getWorkTypes());
        assertTrue(contractor.getWorkTypes().isEmpty());
    }

    @Test
    public void testContractorWithMultipleWorkTypes() {
        // Arrange
        Set<WorkType> workTypes = new HashSet<>();
        workTypes.add(workType1);
        workTypes.add(workType2);

        // Act
        Contractor contractor = Contractor.builder()
                .firstName("Multi")
                .lastName("Skill")
                .workTypes(workTypes)
                .build();

        // Assert
        assertEquals(2, contractor.getWorkTypes().size());
        assertTrue(contractor.getWorkTypes().contains(workType1));
        assertTrue(contractor.getWorkTypes().contains(workType2));
    }

    @Test
    public void testContractorEquality() {
        // Arrange
        Contractor contractor1 = Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        Contractor contractor2 = Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        Contractor contractor3 = Contractor.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        // Assert
        assertEquals(contractor1, contractor2);
        assertNotEquals(contractor1, contractor3);
    }

    @Test
    public void testContractorHashCode() {
        // Arrange
        Contractor contractor1 = Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        Contractor contractor2 = Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        // Assert
        assertEquals(contractor1.hashCode(), contractor2.hashCode());
    }

    @Test
    public void testContractorToString() {
        // Arrange
        Contractor contractor = Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        // Act
        String result = contractor.toString();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testContractorWithEmptyStrings() {
        // Act
        Contractor contractor = Contractor.builder()
                .firstName("")
                .lastName("")
                .email("")
                .phone("")
                .companyName("")
                .address("")
                .city("")
                .country("")
                .build();

        // Assert
        assertEquals("", contractor.getFirstName());
        assertEquals("", contractor.getLastName());
        assertEquals("", contractor.getEmail());
        assertEquals("", contractor.getPhone());
        assertEquals("", contractor.getCompanyName());
        assertEquals("", contractor.getAddress());
        assertEquals("", contractor.getCity());
        assertEquals("", contractor.getCountry());
    }

    @Test
    public void testContractorWithSpecialCharacters() {
        // Act
        Contractor contractor = Contractor.builder()
                .firstName("Józef")
                .lastName("Kowalski-Nowak")
                .email("jozef.kowalski-nowak@test.com")
                .phone("+48 123 456 789")
                .companyName("Józef & Sons Sp. z o.o.")
                .address("ul. Długa 123/45")
                .city("Kraków")
                .postalCode("31-001")
                .country("Polska")
                .nip("123-456-78-90")
                .regon("12345678901")
                .bankAccount("PL 12 3456 7890 1234 5678 9012 3456")
                .build();

        // Assert
        assertEquals("Józef", contractor.getFirstName());
        assertEquals("Kowalski-Nowak", contractor.getLastName());
        assertEquals("jozef.kowalski-nowak@test.com", contractor.getEmail());
        assertEquals("+48 123 456 789", contractor.getPhone());
        assertEquals("Józef & Sons Sp. z o.o.", contractor.getCompanyName());
        assertEquals("ul. Długa 123/45", contractor.getAddress());
        assertEquals("Kraków", contractor.getCity());
        assertEquals("31-001", contractor.getPostalCode());
        assertEquals("Polska", contractor.getCountry());
        assertEquals("123-456-78-90", contractor.getNip());
        assertEquals("12345678901", contractor.getRegon());
        assertEquals("PL 12 3456 7890 1234 5678 9012 3456", contractor.getBankAccount());
    }

    @Test
    public void testContractorInheritanceFromBaseEntity() {
        // Arrange & Act
        Contractor contractor = Contractor.builder()
                .id(1L)
                .name("Test Contractor")
                .firstName("Test")
                .lastName("User")
                .build();

        // Assert
        assertNotNull(contractor);
        // Contractor extends BaseEntity, so it should inherit base properties
        assertEquals(1L, contractor.getId());
        assertEquals("Test Contractor", contractor.getName());
    }

    @Test
    public void testContractorBuilderPattern() {
        // Act
        Contractor contractor = Contractor.builder()
                .id(10L)
                .name("Builder Test")
                .firstName("Builder")
                .lastName("Test")
                .email("builder.test@example.com")
                .phone("111222333")
                .type(EnumContractorType.PRIVATE)
                .build();

        // Assert
        assertEquals(10L, contractor.getId());
        assertEquals("Builder Test", contractor.getName());
        assertEquals("Builder", contractor.getFirstName());
        assertEquals("Test", contractor.getLastName());
        assertEquals("builder.test@example.com", contractor.getEmail());
        assertEquals("111222333", contractor.getPhone());
        assertEquals(EnumContractorType.PRIVATE, contractor.getType());
    }

    @Test
    public void testContractorWithNullValues() {
        // Act
        Contractor contractor = Contractor.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@example.com")
                .phone("123456789")
                .companyName(null)
                .nip(null)
                .regon(null)
                .address(null)
                .city(null)
                .postalCode(null)
                .country(null)
                .bankAccount(null)
                .description(null)
                .build();

        // Assert
        assertEquals("Test", contractor.getFirstName());
        assertEquals("User", contractor.getLastName());
        assertNull(contractor.getCompanyName());
        assertNull(contractor.getNip());
        assertNull(contractor.getRegon());
        assertNull(contractor.getAddress());
        assertNull(contractor.getCity());
        assertNull(contractor.getPostalCode());
        assertNull(contractor.getCountry());
        assertNull(contractor.getBankAccount());
        assertNull(contractor.getDescription());
    }

    @Test
    public void testContractorWorkTypesManipulation() {
        // Arrange
        Contractor contractor = new Contractor();
        Set<WorkType> workTypes = new HashSet<>();

        // Act - Add work types
        workTypes.add(workType1);
        contractor.setWorkTypes(workTypes);

        // Assert
        assertEquals(1, contractor.getWorkTypes().size());
        assertTrue(contractor.getWorkTypes().contains(workType1));

        // Act - Add another work type
        contractor.getWorkTypes().add(workType2);

        // Assert
        assertEquals(2, contractor.getWorkTypes().size());
        assertTrue(contractor.getWorkTypes().contains(workType2));

        // Act - Remove work type
        contractor.getWorkTypes().remove(workType1);

        // Assert
        assertEquals(1, contractor.getWorkTypes().size());
        assertFalse(contractor.getWorkTypes().contains(workType1));
        assertTrue(contractor.getWorkTypes().contains(workType2));
    }
}
