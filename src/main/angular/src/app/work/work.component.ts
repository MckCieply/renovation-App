import {Component, inject, OnInit} from '@angular/core';
import {WorkService} from "./work.service";
import {MatDialog} from "@angular/material/dialog";
import {WorkDialogComponent} from "./work-dialog/work-dialog.component";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";

@Component({
  selector: 'app-work',
  templateUrl: './work.component.html',
  styleUrl: './work.component.scss'
})
export class WorkComponent implements OnInit {
  tableColumns = ['type', 'createdAt', 'room', 'paid', 'actions'];
  works: any;

  worksService = inject(WorkService)

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.worksService.getAllWorks().subscribe({
      next: (data) => this.works = data,
      error: (err) => console.error(err)
    });
  }

  createForm() {
    const dialogRef = this.dialog.open(WorkDialogComponent, {
      data: {action: 'Add'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.worksService.addWork(result).subscribe({
          next: (data) => this.works = [...this.works, data],
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
            this.works = this.works.filter((r: { id: any; }) => r.id !== work.id);
            this.works.push(data);
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
          next: (data) => this.works = this.works.filter((w: { id: any; }) => w.id !== work.id),
          error: (err) => console.error(err)
        });
      }
    });
  }


}
