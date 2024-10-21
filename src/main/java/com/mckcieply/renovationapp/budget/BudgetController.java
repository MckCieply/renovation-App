package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseController;
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
public class BudgetController extends BaseController<Budget, Long> {

    private final BudgetService budgetService;
    public BudgetController(BudgetService budgetService) {
        super(budgetService);
        this.budgetService = budgetService;
    }

    /**
     * Retrieves the current budget.
     *
     * @return a ResponseEntity containing the Budget and HTTP status
     */
    @GetMapping("/get")
    private ResponseEntity<Budget> getBudget() {
        Budget budget = budgetService.getBudget();
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }

}
