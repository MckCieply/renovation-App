package com.mckcieply.renovationapp.contractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "http://localhost:4200")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    @GetMapping("/all")
    public List<Contractor> getAllContractors(){
        return contractorService.getAllContractors();
    }

    @PostMapping("/add")
    public void addContractor(@RequestBody Contractor contractor){
        contractorService.addContractor(contractor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContractor(@PathVariable("id") Long id){
        contractorService.deleteContractor(id);
    }

    @PutMapping("/update")
    public void updateContractor(@RequestBody Contractor contractor){
        contractorService.updateContractor(contractor);
    }
}
