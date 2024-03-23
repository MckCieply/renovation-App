import {Component, Inject} from '@angular/core';
import {ErrorStateMatcher} from "@angular/material/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-edit-contractor-dialog',
  templateUrl: './edit-contractor-dialog.component.html',
  styles: ``
})
export class EditContractorDialogComponent {

  matcher = new ErrorStateMatcher();
  constructor(public dialogRef: MatDialogRef<EditContractorDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public contractor: {
                firstName: string,
                lastName: string,
                email: string,
                phone: string,
                type: string,
                companyName: string,
                nip: string,
                regon: string,
                address: string
                city: string,
                postalCode: string;
                country: string;
                bankAccount: string;
                description: string;
              }) {}
}
