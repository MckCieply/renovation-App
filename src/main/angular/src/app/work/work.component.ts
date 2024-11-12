import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {WorkService} from "./work.service";
import {MatDialog} from "@angular/material/dialog";
import {WorkDialogComponent} from "./work-dialog/work-dialog.component";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-work',
  templateUrl: './work.component.html',
  styleUrl: './work.component.scss'
})
export class WorkComponent implements OnInit {
  tableColumns = ['type', 'room', 'paid','updatedAt', 'actions'];
  dataSource = new MatTableDataSource<any>;

  @ViewChild(MatSort) sort!: MatSort;

  worksService = inject(WorkService)

  constructor(public dialog: MatDialog) {
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


}
