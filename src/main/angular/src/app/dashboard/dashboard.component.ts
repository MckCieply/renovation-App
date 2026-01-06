import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { BudgetService } from "../budget/budget.service";
import { DashboardService } from "./dashboard.service";
import { BreakpointObserver, Breakpoints } from "@angular/cdk/layout";
import { Color, ScaleType } from "@swimlane/ngx-charts";
import { Subject, takeUntil } from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit, OnDestroy {
  // --- Services ---
  private budgetService = inject(BudgetService);
  private dashboardService = inject(DashboardService);
  private breakpointObserver = inject(BreakpointObserver);

  // --- Layout & Cleanup ---
  cols = 2;
  rowHeight = '2:1';
  private destroy$ = new Subject<void>();

  // --- Chart Data ---
  breakdownCost: any[] = [];
  roomHealth: any[] = [];
  budgetChartData: any[] = [];

  // Only used for the "Total" center label in donut chart (optional logic)
  totalCost: number = 0;
  totalBudget: any;

  // --- Color Schemes ---

  // 1. Cost Breakdown (Donut)
  costColorScheme: Color = {
    name: 'costScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  // 2. Room Health (Grouped Bar)
  // [Planned (Purple), Spent (Blue)]
  roomColorScheme: Color = {
    name: 'roomScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#a8385d', '#7aa3e5']
  };

  // 3. Total Budget (Donut)
  budgetColorScheme: Color = {
    name: 'budgetScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#10b981', '#ef4444', '#f59e0b'] // Green (Remaining), Red (Spent), Amber (Allocated)
  };

  // --- Static Legend Data ---
  // Since the bar chart series names are static ("Planned", "Spent"), we define them manually for the legend.
  roomLegendData = [
    { name: 'Planned' },
    { name: 'Spent' }
  ];

  ngOnInit(): void {
    this.setupResponsiveLayout();
    this.loadAllData();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private setupResponsiveLayout(): void {
    this.breakpointObserver.observe([Breakpoints.Small, Breakpoints.Handset])
      .pipe(takeUntil(this.destroy$))
      .subscribe(result => {
        if (result.matches) {
          this.cols = 1;
          this.rowHeight = '1:1.2'; // Taller tiles on mobile to fit content
        } else {
          this.cols = 2;
          this.rowHeight = '2:1';
        }
      });
  }

  private loadAllData(): void {
    // 1. Cost Breakdown
    this.dashboardService.getCostBreakdown()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data: any) => {
          this.breakdownCost = [
            { name: 'Labor', value: data.laborTotal },
            { name: 'Materials', value: data.materialTotal }
          ];
          this.totalCost = data.laborTotal + data.materialTotal;
        },
        error: (err) => console.error('Cost Breakdown error', err)
      });

    // 2. Room Health
    this.dashboardService.getRoomHealth()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data: any[]) => {
          this.roomHealth = data.map(item => ({
            name: item.roomName,
            series: [
              { name: 'Planned', value: item.budgetPlanned },
              { name: 'Spent', value: item.actualSpent }
            ]
          }));
        },
        error: (err) => console.error('Room Health error', err)
      });

    // 3. Total Budget
    this.budgetService.getBudget()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
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
        },
        error: (err) => console.error('Budget error', err)
      });
  }
}
