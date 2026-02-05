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
  budgetUtilizationData: any[] = [];
  budgetUtilizationPercent: number = 0;
  isBudgetOverallocated: boolean = false;
  gaugeMax: number = 100; // Dynamic max for gauge chart
  gaugeBigSegments: number = 4; // Dynamic segments to keep 25% spacing

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
  // [Planned (Purple), Estimated (Amber), Paid (Blue)]
  roomColorScheme: Color = {
    name: 'roomScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#a8385d', '#f59e0b', '#7aa3e5']
  };

  // 3. Total Budget (Donut)
  // [Available (Green), Work in Progress (Amber), Paid Work (Blue)]
  budgetColorScheme: Color = {
    name: 'budgetScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#10b981', '#f59e0b', '#3b82f6'] // Green (Available), Amber (In Progress), Blue (Paid)
  };

  // 4. Budget Utilization (Gauge-like Donut)
  budgetUtilizationColorScheme: Color = {
    name: 'utilizationScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#10b981', '#e5e7eb'] // Green (Used), Gray (Free) - will change dynamically
  };

  // --- Static Legend Data ---
  // Since the bar chart series names are static ("Planned", "Estimated", "Paid"), we define them manually for the legend.
  roomLegendData = [
    { name: 'Planned' },
    { name: 'Estimated' },
    { name: 'Paid' }
  ];

  // Gauge value formatting function
  gaugeValueFormatting = (value: number) => `${value.toFixed(0)}%`;

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
              { name: 'Planned', value: item.budgetPlanned ?? 0 },
              { name: 'Estimated', value: item.estimatedCost ?? 0 },
              { name: 'Paid', value: item.paidCost ?? 0 }
            ]
          }));
        },
        error: (err) => console.error('Room Health error', err)
      });

    // 3. Total Budget - Use budget validation for calculated totals
    this.budgetService.validateBudget()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.totalBudget = data;
          this.budgetChartData = [
            {
              name: 'Available',
              value: Math.max(0, data.availableBudget)
            },
            {
              name: 'Work in Progress',
              value: data.totalEstimatedCosts
            },
            {
              name: 'Paid Work',
              value: data.totalPaidCosts
            }
          ];

          // 4. Budget Utilization Chart - shows allocated/spent vs free
          this.isBudgetOverallocated = data.overallocated;
          this.budgetUtilizationPercent = data.budgetLimit > 0
            ? (data.totalRoomBudgets / data.budgetLimit) * 100
            : 0;

          // Dynamic gauge max - rounds up to nearest 50% above actual value, minimum 100%
          this.gaugeMax = Math.max(100, Math.ceil(this.budgetUtilizationPercent / 50) * 50);
          // Keep 25% spacing: segments = max / 25
          this.gaugeBigSegments = this.gaugeMax / 25;

          // Update color scheme based on overallocation
          this.budgetUtilizationColorScheme = {
            ...this.budgetUtilizationColorScheme,
            domain: data.overallocated
              ? ['#ef4444', '#e5e7eb']  // Red for overallocated
              : ['#10b981', '#e5e7eb']  // Green for healthy
          };

          if (data.overallocated) {
            // Over 100% - show full allocation with excess
            this.budgetUtilizationData = [
              { name: 'Allocated', value: data.totalRoomBudgets }
            ];
          } else {
            // Under or at 100% - show used vs free
            this.budgetUtilizationData = [
              { name: 'Allocated', value: data.totalRoomBudgets },
              { name: 'Free', value: Math.max(0, data.availableBudget) }
            ];
          }
        },
        error: (err) => console.error('Budget error', err)
      });
  }
}
