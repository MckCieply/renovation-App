import {Component, inject, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RoomService} from "../../room/room.service";
import {WorkService} from "../work.service";
import {WorkTypeService} from "../../work-type/work-type.service";

@Component({
  selector: 'app-work-dialog',
  templateUrl: './work-dialog.component.html',
  styles: ``
})
export class WorkDialogComponent implements OnInit{
  rooms: any;
  status: any;
  workTypes: any;
  roomService= inject(RoomService);
  workService = inject(WorkService);
  workTypeService = inject(WorkTypeService);
  constructor(public dialogRef: MatDialogRef<WorkDialogComponent>,
              @Inject(MAT_DIALOG_DATA)
              public data: {
                description: string,
                estMaterialCost: number,
                estLaborCost: number,
                finalMaterialCost: number,
                finalLaborCost: number,
                state: string,
                paid: boolean,
                startDate: string,
                endDate: string,
                room: object,
                workType: object,

                action: string,
              }){
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
