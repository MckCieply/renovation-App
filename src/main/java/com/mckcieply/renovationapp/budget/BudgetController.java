package com.mckcieply.renovationapp.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping("/get")
    private ResponseEntity<Budget> getBudget(){
        Budget budget = budgetService.getBudget();
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget){
        budgetService.updateBudget(budget);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }
}
