import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-room-dialog',
  templateUrl: './room-dialog.component.html',
  styles: ``
})
export class RoomDialogComponent {
  matcher = new ErrorStateMatcher();
  roomForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<RoomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder) {
    //{name: string, budgetPlanned: number, action: string}
    this.roomForm = this.fb.group({
      name: [data.name, Validators.required],
      budgetPlanned: [data.budgetPlanned, Validators.required]
    });
  }

  onSubmit() {
    if (this.roomForm.valid) {
      this.dialogRef.close(this.roomForm.value);
    }
  }
}
