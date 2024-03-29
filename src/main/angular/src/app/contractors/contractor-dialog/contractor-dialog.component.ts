import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-contractor-dialog',
  templateUrl: './contractor-dialog.component.html',
  styles: ``
})
export class ContractorDialogComponent {

  constructor(public dialogRef: MatDialogRef<ContractorDialogComponent>,
              @Inject(MAT_DIALOG_DATA)
              public data: {
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

                action: string
              }) {
  }

}
