import {Component, Inject} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-work-type-dialog',
  templateUrl: './work-type-dialog.component.html',
  styles: ``
})
export class WorkTypeDialogComponent {
    matcher = new ErrorStateMatcher();
    constructor(public dialogRef: MatDialogRef<WorkTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string, action:string}) {
    }

}
