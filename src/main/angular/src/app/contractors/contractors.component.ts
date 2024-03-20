import {Component, inject, OnInit} from '@angular/core';
import {ContractorsService} from "./contractors.service";
import {AddContractorDialogComponent} from "./add-contractor-dialog/add-contractor-dialog.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-contractors',
  templateUrl: './contractors.component.html',
  styleUrl: './contractors.component.scss'
})
export class ContractorsComponent implements OnInit{

  contractors: any;
  contractorsService = inject(ContractorsService);
  constructor(public dialog: MatDialog) {}

  ngOnInit() {
    this.contractorsService.getAllContractors().subscribe({
      next: (data) => this.contractors = data,
      error: (err) => console.error(err)
    });
  }

  createForm(){
    const dialogRef = this.dialog.open(AddContractorDialogComponent, {
      data: {
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        type: "",
        companyName: "",
        nip: "",
        regon: "",
        address: "",
        city: "",
        postalCode: "",
        country: "",
        bankAccount: "",
        description: ""
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
      if(result)
        this.contractorsService.addContractor(result).subscribe({
          next: (data) => this.contractors.push(data),
          error: (err) => console.error(err)
      });
    });
  }

}
