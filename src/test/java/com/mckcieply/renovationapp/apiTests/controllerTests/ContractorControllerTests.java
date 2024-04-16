package com.mckcieply.renovationapp.apiTests.controllerTests;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.contractor.Contractor;
import com.mckcieply.renovationapp.contractor.ContractorController;
import com.mckcieply.renovationapp.contractor.ContractorService;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class ContractorControllerTests extends BaseControllerTests<Contractor, ContractorService>{

    @Mock
    private ContractorService service;

    @Mock
    private ContractorController controller;

    @Override
    protected ContractorService service() {
        return service;
    }

    @Override
    protected BaseController<Contractor, Long> controller() {
        return controller;
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
        return Contractor.builder().id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .phone("123456789")
                .build();
    }
}
