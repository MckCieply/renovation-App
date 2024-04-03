import {Component, inject, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RoomService} from "../../room/room.service";
import {WorkService} from "../work.service";
import {WorkTypeService} from "../../work-type/work-type.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-work-dialog',
  templateUrl: './work-dialog.component.html',
  styles: ``
})
export class WorkDialogComponent implements OnInit{
  workForm: FormGroup;
  rooms: any;
  status: any;
  workTypes: any;
  roomService= inject(RoomService);
  workService = inject(WorkService);
  workTypeService = inject(WorkTypeService);
  constructor(public dialogRef: MatDialogRef<WorkDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder){
    this.workForm = this.fb.group({
        description: [data.description],
        estMaterialCost: [data.estMaterialCost],
        estLaborCost: [data.estLaborCost],
        finalMaterialCost: [data.finalMaterialCost],
        finalLaborCost: [data.finalLaborCost],
        state: [data.state],
        paid: [data.paid],
        startDate: [data.startDate],
        endDate: [data.endDate],
        room: [data.room],
        workType: [data.workType]
    });

  }

  onSubmit(){
    if(this.workForm.valid){
      this.dialogRef.close(this.workForm.value);
    }
  }

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });

    this.workService.getEnumWorkStatus().subscribe({
      next: (data) => this.status = data,
      error: (err) => console.error(err)
    });

    this.workTypeService.getAllTypes().subscribe({
      next: (data) => this.workTypes = data,
      error: (err) => console.error(err)
    });
  }


}
