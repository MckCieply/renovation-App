package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing budget-related operations.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class BudgetService{

    @Autowired
    private BudgetRepository budgetRepository;


    /**
     * Retrieves the current budget.
     *
     * @return the first Budget in the repository
     */
    public Budget getBudget() {
        return budgetRepository.findAll().get(0);
    }

    /**
     * Initializes the budget if none exists.
     * Creates a new Budget with a value of 0.
     */
    public void budgetInit() {
        if (budgetRepository.findAll().isEmpty()) {
            Budget budget = new Budget();
            budget.setBudgetLimit(0);
            budget.setBudgetSpent(0);
            budget.setBudgetAllocated(0);
            budgetRepository.save(budget);
        }
    }
}
