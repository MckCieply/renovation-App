import {Component, inject, OnInit} from '@angular/core';
import {ContractorsService} from "./contractors.service";
import {AddContractorDialogComponent} from "./add-contractor-dialog/add-contractor-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {EditContractorDialogComponent} from "./edit-contractor-dialog/edit-contractor-dialog.component";

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

  removeForm(contractor:any){
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.contractorsService.deleteContractor(contractor).subscribe({
          next: (data) => this.contractors = this.contractors.filter((r: { id: any; }) => r.id !== contractor.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

  editForm(contractor:any){
    const dialogRef = this.dialog.open(EditContractorDialogComponent, {
      data: {...contractor}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.contractorsService.updateContractor(result).subscribe({
          next: (data) => {
            this.contractors = this.contractors.filter((r: { id: any; }) => r.id !== contractor.id);
            this.contractors.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

}
