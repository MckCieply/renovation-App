import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-add-room-dialog',
  templateUrl: './add-room-dialog.component.html',
  styles: ``
})
export class AddRoomDialogComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<AddRoomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number}) {


  }
}
