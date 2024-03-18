import {Component, Inject} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-edit-work-type-dialog',
  templateUrl: './edit-work-type-dialog.component.html',
  styles: ``
})
export class EditWorkTypeDialogComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<EditWorkTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number}) {}
}
