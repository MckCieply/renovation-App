import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {WorkTypeService} from "./work-type.service";
import {WorkTypeDialogComponent} from "./work-type-dialog/work-type-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-work-type',
  templateUrl: './work-type.component.html',
  styleUrl: './work-type.component.scss'
})
export class WorkTypeComponent implements OnInit {
  tableColumns = ['name', 'actions'];
  dataSource = new MatTableDataSource<any>;

  @ViewChild(MatSort) sort!: MatSort;

  workTypeService = inject(WorkTypeService)

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.workTypeService.getAllTypes().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  createForm() {
    const dialogRef = this.dialog.open(WorkTypeDialogComponent, {
      data: {action: 'Add'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.workTypeService.addType(result).subscribe({
          next: (data) => this.dataSource.data = [...this.dataSource.data, data],
          error: (err) => console.error(err)
        });
    });
  }

  editForm(type: any) {
    const dialogRef = this.dialog.open(WorkTypeDialogComponent, {
      data: {...type, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.workTypeService.updateType(result).subscribe({
          next: (data) => {
            this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== type.id);
            this.dataSource.data.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(type: any) {
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.workTypeService.deleteType(type).subscribe({
          next: (data) => this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== type.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

}
