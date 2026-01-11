package com.mckcieply.renovationapp.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Budget> getBudget() {
        Budget budget = budgetService.getBudget();
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

    /**
     * Validates the current budget allocation including room budgets.
     * Returns warnings if room budgets exceed available budget.
     *
     * @return a ResponseEntity containing BudgetValidationDto with validation results
     */
    @GetMapping("/validate")
    public ResponseEntity<BudgetValidationDto> validateBudget() {
        BudgetValidationDto validation = budgetService.validateBudget();
        return new ResponseEntity<>(validation, HttpStatus.OK);
    }

    /**
     * Updates the budget limit and returns validation results.
     *
     * @param budget the Budget object containing the new budgetLimit
     * @return a ResponseEntity containing BudgetValidationDto with validation results
     */
    @PutMapping("/update")
    public ResponseEntity<BudgetValidationDto> updateBudget(@RequestBody Budget budget) {
        BudgetValidationDto validation = budgetService.updateBudgetLimit(budget.getBudgetLimit());
        return new ResponseEntity<>(validation, HttpStatus.OK);
    }

}
