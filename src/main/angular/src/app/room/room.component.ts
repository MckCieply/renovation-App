import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {RoomService} from "./room.service";
import {RoomDialogComponent} from "./room-dialog/room-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {BudgetService} from "../budget/budget.service";
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from "@angular/material/sort";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE_OPTIONS} from "../shared/config/paginator/paginator-config";
import {Budget} from "../shared/models/budget.model";
import {NotificationService} from "../shared/services/notification.service";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit {
  protected readonly DEFAULT_PAGE_SIZE_OPTIONS = DEFAULT_PAGE_SIZE_OPTIONS;
  protected readonly DEFAULT_PAGE_SIZE = DEFAULT_PAGE_SIZE;

  totalBudget: any;
  tableColumns = ['name', 'budgetPlanned', 'budgetShare', 'updatedAt', 'actions'];
  dataSource = new MatTableDataSource<any>;
  filterForm: FormGroup;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  protected readonly Math = Math;

  roomService = inject(RoomService)
  budgetService = inject(BudgetService)
  notificationService = inject(NotificationService)
  fb = inject(FormBuilder)
  dialog = inject(MatDialog)

  constructor() {
    this.filterForm = this.fb.group({
      name: [''],
      minBudgetPlanned: [''],
      maxBudgetPlanned: [''],
      createdBy: [''],
      fromCreatedAt: [''],
      toCreatedAt: [''],
      fromUpdatedAt: [''],
      toUpdatedAt: ['']
    })
  }

  ngOnInit() {
    this.fetchData();

    this.filterForm.valueChanges.subscribe(() => {
      this.loadFiltered();
    });


    this.budgetService.getBudget().subscribe({
      next: (data: Budget) => this.totalBudget = data.budgetLimit,
      error: (err) => console.error(err)
    });

    this.dataSource.sortingDataAccessor = (item, property) => {
      if (property === 'budgetShare') {
        return (Math.round(item.budgetPlanned / this.totalBudget * 100) || 0);
      } else {
        return item[property];
      }
    };
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  createForm() {
    const dialogRef = this.dialog.open(RoomDialogComponent, {
      data: {action: "Add"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.roomService.addRoom(result).subscribe({
          next: () => this.refreshData(),
          error: (err) => console.error(err)
        });
    });
  }

  editForm(room: any) {
    const dialogRef = this.dialog.open(RoomDialogComponent, {
      data: {...room, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.roomService.updateRoom(result).subscribe({
          next: () => this.refreshData(),
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(room: any) {
    // First check if room can be deleted
    this.roomService.canDeleteRoom(room.id).subscribe({
      next: (response) => {
        if (!response.canDelete) {
          // Show error toast if room cannot be deleted
          this.notificationService.showError(response.message);
          return;
        }

        // If room can be deleted, show confirmation dialog
        const dialogRef = this.dialog.open(RemoveDialogComponent)

        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            this.roomService.deleteRoom(room).subscribe({
              next: () => this.refreshData(),
              error: (err) => {
                console.error('Delete error:', err);
                this.notificationService.showError("Failed to delete room");
              }
            });
          }
        });
      },
      error: (err) => {
        console.error('Validation error:', err);
        this.notificationService.showError("Failed to validate room deletion");
      }
    });
  }

  fetchData(){
    this.roomService.getAllRooms().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  /**
   * Refreshes data respecting current filter state.
   * If any filters are applied, uses loadFiltered(), otherwise uses fetchData().
   */
  refreshData() {
    if (this.hasActiveFilters()) {
      this.loadFiltered();
    } else {
      this.fetchData();
    }
  }

  /**
   * Checks if any filter fields have values
   */
  private hasActiveFilters(): boolean {
    const filters = this.filterForm.value;
    return Object.values(filters).some(value => value !== '' && value !== null && value !== undefined);
  }

  loadFiltered() {
    const processedFilters = this.prepareFilters();
    this.roomService.filterRooms(processedFilters).subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  prepareFilters(): any {
    const filters = {...this.filterForm.value};

    // List of date fields to convert
    const dateFields = [
      'fromCreatedAt',
      'toCreatedAt',
      'fromUpdatedAt',
      'toUpdatedAt',
    ];

    // Convert each date field to ISO 8601
    dateFields.forEach((field) => {
      if (filters[field]) {
        filters[field] = new Date(filters[field]).toISOString();
      }
    });

    return filters;
  }

  resetDate(field: string) {
    this.filterForm.get(field)?.reset();
  }
}
