import {Component, Inject} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-work-type-dialog',
  templateUrl: './add-work-type-dialog.component.html',
  styles: ``
})
export class AddWorkTypeDialogComponent {
    matcher = new ErrorStateMatcher();
    constructor(public dialogRef: MatDialogRef<AddWorkTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {name: string}) {
    }

}
