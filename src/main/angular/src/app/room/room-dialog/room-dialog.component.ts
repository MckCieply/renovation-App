import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ErrorStateMatcher} from "@angular/material/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BudgetService} from "../../budget/budget.service";

@Component({
  selector: 'app-room-dialog',
  templateUrl: './room-dialog.component.html',
  styleUrl: `./room-dialog.component.scss`
})
export class RoomDialogComponent implements OnInit {
  matcher = new ErrorStateMatcher();
  roomForm: FormGroup;
  budgetWarning: string | null = null;
  availableBudget: number = 0;

  constructor(public dialogRef: MatDialogRef<RoomDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder,
              private budgetService: BudgetService) {
    //{name: string, budgetPlanned: number, action: string}
    this.roomForm = this.fb.group({
      id: [data.id],
      name: [data.name, Validators.required],
      budgetPlanned: [data.budgetPlanned, Validators.required]
    });
  }

  ngOnInit() {
    this.budgetService.validateBudget().subscribe({
      next: (validation) => {
        this.availableBudget = validation.availableBudget;
        this.checkBudget();
      }
    });

    this.roomForm.get('budgetPlanned')?.valueChanges.subscribe(() => {
      this.checkBudget();
    });
  }

  checkBudget() {
    const newBudget = this.roomForm.get('budgetPlanned')?.value || 0;
    const originalBudget = this.data.budgetPlanned || 0;
    const difference = newBudget - originalBudget;

    if (difference > this.availableBudget) {
      const excess = difference - this.availableBudget;
      this.budgetWarning = `WARNING: This budget exceeds available budget by ${excess.toFixed(2)} PLN`;
    } else {
      this.budgetWarning = null;
    }
  }

  onSubmit() {
    if (this.roomForm.valid) {
      this.dialogRef.close(this.roomForm.value);
    }
  }
}
