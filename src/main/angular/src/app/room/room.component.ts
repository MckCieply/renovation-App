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

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit {
  protected readonly DEFAULT_PAGE_SIZE_OPTIONS = DEFAULT_PAGE_SIZE_OPTIONS;
  protected readonly DEFAULT_PAGE_SIZE = DEFAULT_PAGE_SIZE;

  totalBudget: any;
  tableColumns = ['name', 'budgetPlanned', 'budgetShare', 'createdBy', 'updatedAt', 'actions'];
  dataSource = new MatTableDataSource<any>;
  filterForm: FormGroup;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  protected readonly Math = Math;

  roomService = inject(RoomService)
  budgetService = inject(BudgetService)
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
    this.roomService.getAllRooms().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
        },
      error: (err) => console.error(err)

    });

    this.filterForm.valueChanges.subscribe(() => {
      this.loadFiltered();
    });


    this.budgetService.getBudget().subscribe({
      next: (data) => this.totalBudget = data,
      error: (err) => console.error(err)
    });

    this.dataSource.sortingDataAccessor = (item, property) => {
      if (property === 'budgetShare') {
        return (Math.round(item.budgetPlanned / this.totalBudget.value * 100) || 0);
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
          // Table wouldn't refresh on push
          next: (data) => this.dataSource.data = [...this.dataSource.data, data],
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
          next: (data) => {
            this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== room.id);
            this.dataSource.data.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(room: any) {
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.roomService.deleteRoom(room).subscribe({
          next: (data) => this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== room.id),
          error: (err) => console.error(err)
        });
      }
    });
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
