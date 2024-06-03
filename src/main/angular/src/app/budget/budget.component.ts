import {Component, inject, OnInit} from '@angular/core';
import {BudgetService} from "./budget.service";

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrl: './budget.component.scss'
})
export class BudgetComponent implements OnInit {

  budget: any;

  budgetService = inject(BudgetService)

  constructor() {
  }

  updateBudget(budget: any) {
    this.budgetService.updateBudget(budget).subscribe({
      next: (data) => this.budget = data,
      error: (err) => console.error(err)
    });
  }

  ngOnInit() {
    this.budgetService.getBudget().subscribe({
      next: (data) => this.budget = data,
      error: (err) => console.error(err)
    });
  }
}
