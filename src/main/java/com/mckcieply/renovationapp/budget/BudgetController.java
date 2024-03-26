package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController extends BaseController<Budget, Long> {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        super(budgetService);
        this.budgetService = budgetService;
    }

    @GetMapping("/get")
    private ResponseEntity<Budget> getBudget(){
        Budget budget = budgetService.getBudget();
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

}
