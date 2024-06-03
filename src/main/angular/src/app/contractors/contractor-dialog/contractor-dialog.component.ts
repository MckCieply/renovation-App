import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MaskitoOptions} from "@maskito/core";

@Component({
  selector: 'app-contractor-dialog',
  templateUrl: './contractor-dialog.component.html',
  styles: ``
})
export class ContractorDialogComponent {
  contractorForm: FormGroup;

  phoneMask: MaskitoOptions = {
    mask: [/\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/]

  }
  bankingMask: MaskitoOptions = {
    // 2 digits // 6x 4 digits
    mask: [/^\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d/, " ", /\d/, /\d/, /\d/, /\d$/]
  }
  postalMask: MaskitoOptions = {
    // 2 digits // 3 digits
    mask: [/^\d/, /\d/, "-", /\d/, /\d/, /\d/]
  }

  constructor(public dialogRef: MatDialogRef<ContractorDialogComponent>,
              // @Inject(MAT_DIALOG_DATA)
              // public data: {
              //   firstName: string,
              //   lastName: string,
              //   email: string,
              //   phone: string,
              //   type: string,
              //   companyName: string,
              //   nip: string,
              //   regon: string,
              //   address: string
              //   city: string,
              //   postalCode: string;
              //   country: string;
              //   bankAccount: string;
              //   description: string;
              //
              //   action: string}
              @Inject(MAT_DIALOG_DATA) public data: any,
              private fb: FormBuilder) {
    this.contractorForm = this.fb.group({
      firstName: [data.firstName],
      lastName: [data.lastName],
      email: [data.email, Validators.email],
      phone: [data.phone,],
      type: [data.type],
      companyName: [data.companyName],
      nip: [data.nip],
      regon: [data.regon],
      address: [data.address],
      city: [data.city],
      postalCode: [data.postalCode, Validators.pattern(/^\d{2}-\d{3}$/)],
      country: [data.country],
      bankAccount: [data.bankAccount, Validators.pattern(/^\d{2}\s\d{4}\s\d{4}\s\d{4}\s\d{4}\s\d{4}\s\d{4}$/)],
      description: [data.description]
    });
  }

  onSubmit() {
    if (this.contractorForm.valid) {
      this.dialogRef.close(this.contractorForm.value);
    }
  }


}
