export interface BudgetValidation {
  budgetLimit: number;
  totalRoomBudgets: number;
  totalEstimatedCosts: number;
  totalPaidCosts: number;
  availableBudget: number;
  overallocated: boolean;
  warningMessage: string | null;
}

