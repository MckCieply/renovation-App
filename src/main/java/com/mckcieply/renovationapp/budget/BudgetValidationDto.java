package com.mckcieply.renovationapp.budget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for budget validation response including warnings and allocation details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetValidationDto {
    private double budgetLimit;
    private double totalRoomBudgets;
    private double totalEstimatedCosts;
    private double totalPaidCosts;
    private double availableBudget;
    private boolean overallocated;
    private String warningMessage;

    public static BudgetValidationDto from(Budget budget, double totalRoomBudgets,
                                          double totalEstimatedCosts, double totalPaidCosts) {
        BudgetValidationDto dto = new BudgetValidationDto();
        dto.setBudgetLimit(budget.getBudgetLimit());
        dto.setTotalRoomBudgets(totalRoomBudgets);
        dto.setTotalEstimatedCosts(totalEstimatedCosts);
        dto.setTotalPaidCosts(totalPaidCosts);

        double available = budget.getBudgetLimit() - totalRoomBudgets;
        dto.setAvailableBudget(available);

        boolean overallocated = totalRoomBudgets > budget.getBudgetLimit();
        dto.setOverallocated(overallocated);

        if (overallocated) {
            double excess = totalRoomBudgets - budget.getBudgetLimit();
            dto.setWarningMessage(String.format("WARNING: Room budgets exceed total budget by %.2f PLN", excess));
        } else {
            dto.setWarningMessage(null);
        }

        return dto;
    }
}

