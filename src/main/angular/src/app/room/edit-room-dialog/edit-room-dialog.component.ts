import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-edit-room-dialog',
  templateUrl: './edit-room-dialog.component.html',
  styles: ``
})
export class EditRoomDialogComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<EditRoomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number}) {}
}
