package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service class for managing budget-related operations.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class BudgetService extends BaseService<Budget, Long> {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        super(budgetRepository);
        this.budgetRepository = budgetRepository;
    }

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
            budget.setValue(0);
            budgetRepository.save(budget);
        }
    }

    @Override
    protected Class<Budget> getEntityClass() {
        return Budget.class;
    }
}
