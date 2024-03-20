import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-add-contractor-dialog',
  templateUrl: './add-contractor-dialog.component.html',
  styles: ``
})
export class AddContractorDialogComponent {

  constructor(public dialogRef: MatDialogRef<AddContractorDialogComponent>,
              @Inject(MAT_DIALOG_DATA)
              public contractor: {
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
              }) {
  }

}
