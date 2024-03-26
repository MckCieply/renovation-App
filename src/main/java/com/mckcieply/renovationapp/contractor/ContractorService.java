package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService extends BaseService<Contractor, Long> {

    private final ContractorRepository contractorRepository;

    public ContractorService(ContractorRepository contractorRepository) {
        super(contractorRepository);
        this.contractorRepository = contractorRepository;
    }

    @Override
    public List<Contractor> getAll(){
        List<Contractor> contractors = contractorRepository.findAll();
        for(Contractor contractor : contractors)
            if(!contractor.getFirstName().isEmpty() && !contractor.getLastName().isEmpty())
                contractor.setFullName(contractor.getFirstName() + " " + contractor.getLastName());
        return contractors;
    }

}
