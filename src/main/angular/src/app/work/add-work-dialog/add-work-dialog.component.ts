import {Component, inject, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RoomService} from "../../room/room.service";
import {WorkService} from "../work.service";

@Component({
  selector: 'app-add-work-dialog',
  templateUrl: './add-work-dialog.component.html',
  styles: ``
})
export class AddWorkDialogComponent implements OnInit{
  rooms: any;
  status: any;
  roomService= inject(RoomService);
  workService = inject(WorkService);
  constructor(public dialogRef: MatDialogRef<AddWorkDialogComponent>,
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
  }


}
