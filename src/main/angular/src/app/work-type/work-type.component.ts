import {Component, inject, OnInit} from '@angular/core';
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {WorkTypeService} from "./work-type.service";
import {WorkTypeDialogComponent} from "./work-type-dialog/work-type-dialog.component";

@Component({
  selector: 'app-work-type',
  templateUrl: './work-type.component.html',
  styleUrl: './work-type.component.scss'
})
export class WorkTypeComponent implements OnInit{

  types: any;
  workTypeService = inject(WorkTypeService)
  constructor(public dialog: MatDialog) {}
  createForm(){
    const dialogRef = this.dialog.open(WorkTypeDialogComponent, {
      data: {action: 'Add'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result)
        this.workTypeService.addType(result).subscribe({
          next: (data) => this.types.push(data),
          error: (err) => console.error(err)
        });
    });
  }

  removeForm(type:any){
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.workTypeService.deleteType(type).subscribe({
          next: (data) => this.types = this.types.filter((r: { id: any; }) => r.id !== type.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

  editForm(type:any){
    const dialogRef = this.dialog.open(WorkTypeDialogComponent, {
      data: {...type, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.workTypeService.updateType(result).subscribe({
          next: (data) => {
            this.types = this.types.filter((r: { id: any; }) => r.id !== type.id);
            this.types.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  ngOnInit() {
    this.workTypeService.getAllTypes().subscribe({
      next: (data) => this.types = data,
      error: (err) => console.error(err)
    });
  }
}
