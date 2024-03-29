import {Component, inject, OnInit} from '@angular/core';
import {WorkService} from "./work.service";
import {MatDialog} from "@angular/material/dialog";
import {AddWorkDialogComponent} from "./add-work-dialog/add-work-dialog.component";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";

@Component({
  selector: 'app-work',
  templateUrl: './work.component.html',
  styleUrl: './work.component.scss'
})
export class WorkComponent implements OnInit{
  works: any;
  worksService = inject(WorkService)

  constructor(public dialog: MatDialog) {}

  ngOnInit() {
    this.worksService.getAllWorks().subscribe({
      next: (data) => this.works = data,
      error: (err) => console.error(err)
    });
  }

  createForm(){
    const dialogRef = this.dialog.open(AddWorkDialogComponent, {
      data: {
        description: "",
        estMaterialCost: 0,
        estLaborCost: 0,
        finalMaterialCost: 0,
        finalLaborCost: 0,
        state: "",
        paid: false,
        startDate: "",
        endDate: "",

      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result)
        this.worksService.addWork(result).subscribe({
          next: (data) => this.works.push(data),
          error: (err) => console.error(err)
      });
    });
  }

  deleteWork(work: any){
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.worksService.deleteWork(work).subscribe({
          next: (data) => this.works = this.works.filter((w: { id: any; }) => w.id !== work.id),
          error: (err) => console.error(err)
        });
      }
    });
  }


}
