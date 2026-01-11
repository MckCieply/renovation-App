package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.contractor.Contractor;
import com.mckcieply.renovationapp.contractor.ContractorRepository;
import com.mckcieply.renovationapp.contractor.ContractorService;
import com.mckcieply.renovationapp.enumerable.EnumContractorType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ContractorServiceTests extends BaseServiceTests<Contractor, ContractorRepository>{

    @Mock
    ContractorRepository repository;

    @InjectMocks
    ContractorService service;

    @Override
    protected ContractorRepository repository() {
        return repository;
    }

    @Override
    protected BaseService<Contractor, Long> service() {
        return service;
    }

    @Override
    protected List<Contractor> createDummyEntities() {
        List<Contractor> entities = new ArrayList<>();
        entities.add(Contractor.builder().id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .phone("123456789")
                .build());
        entities.add(Contractor.builder().id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@gmail.com")
                .phone("987654321")
                .city("Warsaw")
                .build());
        entities.add(Contractor.builder().id(3L)
                .firstName("John")
                .lastName("Smith")
                .build());
        return entities;
    }

    @Override
    protected Contractor createDummyEntity() {
        return Contractor.builder().id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@gmail.com")
                .phone("987654321")
                .city("Warsaw")
                .type(EnumContractorType.PRIVATE)
                .build();
    }

    @Test
    public void testGetAllWithFullNames() {
        // Arrange
        List<Contractor> contractors = Arrays.asList(
            Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .phone("123456789")
                .build(),
            Contractor.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane@example.com")
                .phone("987654321")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Jane Smith", result.get(1).getFullName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAllWithEmptyNames() {
        // Arrange
        List<Contractor> contractors = Arrays.asList(
            Contractor.builder()
                .id(1L)
                .firstName("")
                .lastName("")
                .email("test@example.com")
                .build(),
            Contractor.builder()
                .id(2L)
                .firstName("John")
                .lastName("")
                .email("john@example.com")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(2, result.size());
        assertNull(result.get(0).getFullName()); // Empty names should not set fullName
        assertNull(result.get(1).getFullName()); // Missing lastName should not set fullName
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAllWithNullNames() {
        // Arrange
        List<Contractor> contractors = Arrays.asList(
            Contractor.builder()
                .id(1L)
                .firstName(null)
                .lastName("Doe")
                .email("test@example.com")
                .build(),
            Contractor.builder()
                .id(2L)
                .firstName("Jane")
                .lastName(null)
                .email("jane@example.com")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act & Assert
        assertThrows(NullPointerException.class,
            () -> service.getAll());
    }

    @Test
    public void testGetAllEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testConstructor() {
        // Arrange
        ContractorRepository mockRepo = mock(ContractorRepository.class);

        // Act
        ContractorService newService = new ContractorService(mockRepo);

        // Assert
        assertNotNull(newService);
    }

    @Test
    public void testGetAllWithMixedValidInvalidNames() {
        // Arrange
        List<Contractor> contractors = Arrays.asList(
            Contractor.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build(),
            Contractor.builder()
                .id(2L)
                .firstName("")
                .lastName("Smith")
                .build(),
            Contractor.builder()
                .id(3L)
                .firstName("Alice")
                .lastName("Johnson")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertNull(result.get(1).getFullName()); // Empty firstName should not set fullName
        assertEquals("Alice Johnson", result.get(2).getFullName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAllWithSpecialCharactersInNames() {
        // Arrange
        List<Contractor> contractors = Arrays.asList(
            Contractor.builder()
                .id(1L)
                .firstName("Józef")
                .lastName("Kowalski-Nowak")
                .build(),
            Contractor.builder()
                .id(2L)
                .firstName("María José")
                .lastName("García-López")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Józef Kowalski-Nowak", result.get(0).getFullName());
        assertEquals("María José García-López", result.get(1).getFullName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAllWithSingleCharacterNames() {
        // Arrange
        List<Contractor> contractors = List.of(
            Contractor.builder()
                .id(1L)
                .firstName("A")
                .lastName("B")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals("A B", result.get(0).getFullName());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetAllWithWhitespaceInNames() {
        // Arrange
        List<Contractor> contractors = List.of(
            Contractor.builder()
                .id(1L)
                .firstName(" John ")
                .lastName(" Doe ")
                .build()
        );

        when(repository.findAll()).thenReturn(contractors);

        // Act
        List<Contractor> result = service.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(" John   Doe ", result.get(0).getFullName()); // Preserves original whitespace
        verify(repository, times(1)).findAll();
    }
}
