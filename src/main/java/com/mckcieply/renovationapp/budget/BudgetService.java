package com.mckcieply.renovationapp.budget;

import com.mckcieply.renovationapp.room.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing budget-related operations.
 */
@Service
public class BudgetService{

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private RoomRepository roomRepository;

    /**
     * Retrieves the current budget.
     *
     * @return the first Budget in the repository
     */
    public Budget getBudget() {
        return budgetRepository.findAll().get(0);
    }

    /**
     * Gets the total sum of all room budgets.
     * @return the total of all budgetPlanned values from rooms
     */
    public double getTotalRoomBudgets() {
        return roomRepository.findAll().stream()
                .mapToDouble(room -> room.getBudgetPlanned() != null ? room.getBudgetPlanned() : 0)
                .sum();
    }

    /**
     * Gets total estimated costs for work in progress.
     * @return sum of all estimated costs from works
     */
    public double getTotalEstimatedCosts() {
        Double total = roomRepository.getTotalEstimatedCosts();
        return total != null ? total : 0.0;
    }

    /**
     * Gets total paid costs for completed work.
     * @return sum of all paid work costs
     */
    public double getTotalPaidCosts() {
        Double total = roomRepository.getTotalPaidCosts();
        return total != null ? total : 0.0;
    }

    /**
     * Validates the current budget allocation including room budgets.
     * @return BudgetValidationDto with validation results and warnings
     */
    public BudgetValidationDto validateBudget() {
        Budget budget = getBudget();
        double totalRoomBudgets = getTotalRoomBudgets();
        double totalEstimated = getTotalEstimatedCosts();
        double totalPaid = getTotalPaidCosts();
        return BudgetValidationDto.from(budget, totalRoomBudgets, totalEstimated, totalPaid);
    }

    /**
     * Updates the budget limit and returns validation results.
     * @param newLimit the new budget limit
     * @return BudgetValidationDto with updated budget and validation
     */
    @Transactional
    public BudgetValidationDto updateBudgetLimit(double newLimit) {
        Budget budget = getBudget();
        budget.setBudgetLimit(newLimit);
        budgetRepository.save(budget);
        return validateBudget();
    }

    /**
     * Initializes the budget if none exists.
     * Creates a new Budget with a limit of 0.
     */
    public void budgetInit() {
        if (budgetRepository.findAll().isEmpty()) {
            Budget budget = new Budget();
            budget.setBudgetLimit(0);
            budgetRepository.save(budget);
        }
    }
}
