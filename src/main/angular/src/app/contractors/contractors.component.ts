import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {ContractorsService} from "./contractors.service";
import {ContractorDialogComponent} from "./contractor-dialog/contractor-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-contractors',
  templateUrl: './contractors.component.html',
  styleUrl: './contractors.component.scss'
})
export class ContractorsComponent implements OnInit {
  tableColumns = ['fullName', 'type', 'email', 'phone', 'actions'];
  dataSource = new MatTableDataSource<any>;

  contractorsService = inject(ContractorsService);

  @ViewChild(MatSort) sort!: MatSort;
  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.contractorsService.getAllContractors().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
      },
      error: (err) => console.error(err)
    });
  }

  createForm() {
    const dialogRef = this.dialog.open(ContractorDialogComponent, {
      data: {action: "Add"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.contractorsService.addContractor(result).subscribe({
          next: (data) => this.dataSource.data = [...this.dataSource.data, data],
          error: (err) => console.error(err)
        });
    });
  }

  editForm(contractor: any) {
    const dialogRef = this.dialog.open(ContractorDialogComponent, {
      data: {...contractor, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.contractorsService.updateContractor(result).subscribe({
          next: (data) => {
            this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== contractor.id);
            this.dataSource.data.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(contractor: any) {
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.contractorsService.deleteContractor(contractor).subscribe({
          next: (data) => this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== contractor.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

}
