import {Component, inject, OnInit} from '@angular/core';
import {BudgetService} from "./budget.service";
import {BudgetValidation} from "../shared/models/budget-validation.model";
import {Budget} from "../shared/models/budget.model";

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrl: './budget.component.scss'
})
export class BudgetComponent implements OnInit {

  budget: Budget = { budgetLimit: 0, budgetSpent: 0, budgetAllocated: 0 };
  validation!: BudgetValidation;

  budgetService = inject(BudgetService)

  constructor() { }

  getProgressBarColor(): 'primary' | 'accent' | 'warn' {
    return this.validation?.overallocated ? 'warn' : 'primary';
  }

  updateBudget(budget: any) {
    this.budgetService.updateBudget(budget).subscribe({
      next: (data) => {
        this.validation = data;
        this.budget.budgetLimit = data.budgetLimit;
        // Reload validation to ensure warning section appears
        this.loadValidation();
      },
      error: (err) => console.error(err)
    });
  }

  loadValidation() {
    this.budgetService.validateBudget().subscribe({
      next: (data) => this.validation = data,
      error: (err) => console.error(err)
    });
  }

  ngOnInit() {
    this.budgetService.getBudget().subscribe({
      next: (data) => {
        this.budget = data;
        this.loadValidation();
      },
      error: (err) => console.error(err)
    });
  }
}
