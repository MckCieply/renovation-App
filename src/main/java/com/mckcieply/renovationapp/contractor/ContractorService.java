package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing contractors in the renovation application.
 * Provides methods for contractor operations such as retrieval and processing.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class ContractorService extends BaseService<Contractor, Long> {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        super(contractorRepository);
        this.contractorRepository = contractorRepository;
    }

    @Override
    protected Class<Contractor> getEntityClass() {
        return Contractor.class;
    }

    /**
     * Retrieves all contractors, setting their full names if both first and last names are provided.
     *
     * @return a list of contractors with their full names populated
     */
    @Override
    public List<Contractor> getAll() {
        List<Contractor> contractors = contractorRepository.findAll();
        for (Contractor contractor : contractors)
            if (!contractor.getFirstName().isEmpty() && !contractor.getLastName().isEmpty())
                contractor.setFullName(contractor.getFirstName() + " " + contractor.getLastName());
        return contractors;
    }

}
