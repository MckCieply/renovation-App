import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-room-dialog',
  templateUrl: './room-dialog.component.html',
  styles: ``
})
export class RoomDialogComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<RoomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number, action: string}) {


  }
}
