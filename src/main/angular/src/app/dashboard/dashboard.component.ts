import {Component, inject, OnInit} from '@angular/core';
import {BudgetService} from "../budget/budget.service";
import {Budget} from "../shared/models/budget.model";
import {DashboardService} from "./dashboard.service";

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

  totalCost: number = 0;
  breakdownCost: any[] = [];


  budgetService = inject(BudgetService)
  dashboardService = inject(DashboardService)

  ngOnInit(): void {
    this.budgetService.getBudget().subscribe(data => {
      this.totalBudget = data;
      this.budgetChartData = [
        {
          name: 'Remaining',
          value: this.totalBudget.budgetLimit - this.totalBudget.budgetSpent - this.totalBudget.budgetAllocated
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

    this.loadCostData();
  }

  loadCostData() {
    this.dashboardService.getCostBreakdown().subscribe({
      next: (data: any) => {
        this.breakdownCost = [
          { name: 'Labor', value: data.laborTotal },
          { name: 'Materials', value: data.materialTotal }
        ];
        // Calculate total for the center label
        this.totalCost = data.laborTotal + data.materialTotal;
      },
      error: (err) => console.error('Failed', err)
    });
  }
}

