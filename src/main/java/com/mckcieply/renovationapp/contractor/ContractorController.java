package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/contractors")
@CrossOrigin(origins = "http://localhost:4200")
public class ContractorController extends BaseController<Contractor, Long> {

    private final ContractorService contractorService;

    public ContractorController(ContractorService contractorService) {
        super(contractorService);
        this.contractorService = contractorService;

    }
}
