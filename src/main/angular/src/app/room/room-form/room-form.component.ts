import {Component, Inject, inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {RoomService} from "../room.service";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-room-form',
  templateUrl: './room-form.component.html',
  styles: ``
})
export class RoomFormComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<RoomFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number}) {


  }
}
