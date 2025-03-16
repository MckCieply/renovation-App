package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing budget-related operations.
 * Extends the BaseController for common CRUD functionalities.
 */
@RestController
@RequestMapping("/api/budget")
@CrossOrigin(origins = "http://localhost:4200")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;


    /**
     * Retrieves the current budget.
     *
     * @return a ResponseEntity containing the Budget and HTTP status
     */
    @GetMapping("/budget")
    private ResponseEntity<Budget> getBudget() {
        Budget budget = budgetService.getBudget();
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

}
