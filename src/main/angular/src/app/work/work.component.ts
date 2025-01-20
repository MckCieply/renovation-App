import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {WorkService} from "./work.service";
import {MatDialog} from "@angular/material/dialog";
import {WorkDialogComponent} from "./work-dialog/work-dialog.component";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {FormBuilder, FormGroup} from "@angular/forms";
import {RoomService} from "../room/room.service";
import {WorkTypeService} from "../work-type/work-type.service";
import {DEFAULT_PAGE_SIZE, DEFAULT_PAGE_SIZE_OPTIONS} from "../shared/config/paginator/paginator-config";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-work',
  templateUrl: './work.component.html',
  styleUrl: './work.component.scss'
})
export class WorkComponent implements OnInit {
  protected readonly DEFAULT_PAGE_SIZE = DEFAULT_PAGE_SIZE;
  protected readonly DEFAULT_PAGE_SIZE_OPTIONS = DEFAULT_PAGE_SIZE_OPTIONS;

  tableColumns = ['type', 'room', 'paid','updatedAt','state', 'actions'];
  dataSource = new MatTableDataSource<any>;
  filterForm: FormGroup;
  rooms: any;
  workTypes: any;
  status: any;

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  worksService = inject(WorkService)
  roomService = inject(RoomService)
  workTypeService = inject(WorkTypeService)
  fb = inject(FormBuilder)
  dialog = inject(MatDialog)

  constructor() {
    this.filterForm = this.fb.group({
      state: [''],
      paid: [''],
      roomId: [''],
      workTypeId: [''],
      description: [''],
    })
  }

  ngOnInit() {
    this.worksService.getAllWorks().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });

    this.dataSource.sortingDataAccessor = (item, property) => {
      switch (property) {
        case 'room':
          return item.room?.name || ''; // Fallback to empty string if `name` is null or undefined
        case 'type':
          return item.workType?.name || ''; // Fallback to empty string if `name` is null or undefined
        default:
          return item[property];
      }
    }

    this.roomService.getMinimal().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });

    this.workTypeService.getMinimal().subscribe({
      next: (data) => this.workTypes = data,
      error: (err) => console.error(err)
    });

    this.filterForm.valueChanges.subscribe(() => {
      this.loadFiltered();
    });

    this.worksService.getEnumWorkStatus().subscribe({
      next: (data) => this.status = data,
      error: (err) => console.error(err)
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  createForm() {
    const dialogRef = this.dialog.open(WorkDialogComponent, {
      data: {action: 'Add'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.worksService.addWork(result).subscribe({
          next: (data) => this.dataSource.data= [...this.dataSource.data, data],
          error: (err) => console.error(err)
        });
    });
  }

  editForm(work: any) {
    const dialogRef = this.dialog.open(WorkDialogComponent, {
      data: {...work, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.worksService.updateWork(result).subscribe({
          next: (data) => {
            this.dataSource.data= this.dataSource.data.filter((r: { id: any; }) => r.id !== work.id);
            this.dataSource.data.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(work: any) {
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.worksService.deleteWork(work).subscribe({
          next: (data) => this.dataSource.data= this.dataSource.data.filter((w: { id: any; }) => w.id !== work.id),
          error: (err) => console.error(err)
        });
      }
    });
  }


  //MatSelect for rooms and workTypes
  loadFiltered(){
    const processedFilters = this.prepareFilters();
    this.worksService.filterWork(processedFilters).subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  prepareFilters(){
    const filters = {...this.filterForm.value}

    return filters
  }

  /**
   * Compares two objects by their ID property
   * Used by mat-select to properly compare objects for selection
   * @param obj1 - First object to compare
   * @param obj2 - Second object to compare
   * @returns boolean indicating if the objects have the same ID
   */
  compareById(obj1: any, obj2: any): boolean {
    return obj1 && obj2 && obj1.id === obj2.id;
  }
}
