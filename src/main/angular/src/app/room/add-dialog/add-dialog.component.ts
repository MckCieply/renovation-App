import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-add-dialog',
  templateUrl: './add-dialog.component.html',
  styles: ``
})
export class AddDialogComponent {
  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<AddDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, budgetPlanned: number}) {


  }
}
