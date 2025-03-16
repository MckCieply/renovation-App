package com.mckcieply.renovationapp.budget;

import jakarta.transaction.Transactional;
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
     * Updates the allocated budget by adding the specified amount.
     * @param allocated the amount to add to the allocated budget
     */
    @Transactional
    public void updateAllocatedBudget(double allocated){
        Budget budget = getBudget();
        budget.setBudgetAllocated(budget.getBudgetAllocated() + allocated);
        budgetRepository.save(budget);
    }

    /**
     * Updates the spent budget by adding the specified amount.
     * @param spent the amount to add to the spent budget
     */
    @Transactional
    public void updateSpentBudget(double spent){
        Budget budget = getBudget();
        budget.setBudgetSpent(budget.getBudgetSpent() + spent);
        budgetRepository.save(budget);
    }

    /**
     * Moves the specified amount from the allocated budget to the spent budget.
     * @param allocatedAmount the amount to move from the allocated budget
     *                        (subtracted from the allocated budget)
     * @param spentAmount the amount to move to the spent budget
     *                    (added to the spent budget)
     */
    @Transactional
    public void moveFromAllocatedToSpent(double allocatedAmount, double spentAmount){
        Budget budget = getBudget();
        budget.setBudgetAllocated(budget.getBudgetAllocated() - allocatedAmount);
        budget.setBudgetSpent(budget.getBudgetSpent() + spentAmount);
        budgetRepository.save(budget);
    }

    /**
     * Moves the specified amount from the spent budget to the allocated budget.
     * @param finalCosts the amount to move from the spent budget
     *                   (subtracted from the spent budget)
     * @param estimatedCosts the amount to move to the allocated budget
     *                       (added to the allocated budget)
     */
    @Transactional
    public void moveFromSpentToAllocated(double finalCosts, double estimatedCosts) {
        Budget budget = getBudget();
        budget.setBudgetSpent(budget.getBudgetSpent() - finalCosts);
        budget.setBudgetAllocated(budget.getBudgetAllocated() + estimatedCosts);
        budgetRepository.save(budget);
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
