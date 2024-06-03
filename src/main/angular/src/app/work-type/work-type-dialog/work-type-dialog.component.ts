import {Component, Inject} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-work-type-dialog',
  templateUrl: './work-type-dialog.component.html',
  styles: ``
})
export class WorkTypeDialogComponent {
  matcher = new ErrorStateMatcher();
  workTypeForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<WorkTypeDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder) {
    this.workTypeForm = this.fb.group({
      name: [data.name, Validators.required],
    });
  }

  onSubmit() {
    if (this.workTypeForm.valid) {
      this.dialogRef.close(this.workTypeForm.value);
    }
  }

}
