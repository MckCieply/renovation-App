package com.mckcieply.renovationapp.contractor;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "http://localhost:4200")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

    @GetMapping("/all")
    public ResponseEntity<List<Contractor>> getAllContractors(){
        List<Contractor> contractors = contractorService.getAllContractors();
        return new ResponseEntity<>(contractors, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Contractor> addContractor(@RequestBody Contractor contractor){
        contractorService.addContractor(contractor);
        return new ResponseEntity<>(contractor, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContractor(@PathVariable("id") Long id){
        contractorService.deleteContractor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Contractor> updateContractor(@RequestBody Contractor contractor){
        contractorService.updateContractor(contractor);
        return new ResponseEntity<>(contractor, HttpStatus.OK);
    }
}
