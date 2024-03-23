package com.mckcieply.renovationapp.contractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractorService {

    @Autowired
    private ContractorRepository contractorRepository;

    public List<Contractor> getAllContractors(){
        List<Contractor> contractors = contractorRepository.findAll();
        for(Contractor contractor : contractors)
            if(!contractor.getFirstName().isEmpty() && !contractor.getLastName().isEmpty())
                contractor.setFullName(contractor.getFirstName() + " " + contractor.getLastName());
        return contractors;
    }

    public void addContractor(Contractor contractor){
        contractorRepository.save(contractor);
    }

    public void deleteContractor(Long id){
        contractorRepository.deleteById(id);
    }

    public void updateContractor(Contractor contractor){
        contractorRepository.save(contractor);
    }
}