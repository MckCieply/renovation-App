import {Component, inject, OnInit} from '@angular/core';
import {BudgetService} from "../budget/budget.service";
import {Budget} from "../shared/models/budget.model";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {
  totalBudget: any;
  budgetChartData: any[] = [];
  budgetColorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C']
  }

  budgetService = inject(BudgetService)

  ngOnInit(): void {
    this.budgetService.getBudget().subscribe(data => {
      this.totalBudget = data;
      this.budgetChartData = [
        {
          name: 'Budget Limit',
          value: this.totalBudget.budgetLimit
        },
        {
          name: 'Spent',
          value: this.totalBudget.budgetSpent
        },
        {
          name: 'Allocated',
          value: this.totalBudget.budgetAllocated
        }
      ];
    });
  }
}
