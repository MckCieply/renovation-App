import {Component, inject, OnInit, ViewChild} from '@angular/core';
import {RoomService} from "./room.service";
import {RoomDialogComponent} from "./room-dialog/room-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {BudgetService} from "../budget/budget.service";
import { MatTableDataSource } from '@angular/material/table';
import {MatSort} from "@angular/material/sort";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit {
  totalBudget: any;
  tableColumns = ['name', 'budgetPlanned', 'budgetShare', 'updatedAt', 'actions'];
  dataSource = new MatTableDataSource<any>;

  @ViewChild(MatSort) sort!: MatSort;

  protected readonly Math = Math;

  roomService = inject(RoomService)
  budgetService = inject(BudgetService)

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => {
        this.dataSource.data = data
        this.dataSource.sort = this.sort;
        },
      error: (err) => console.error(err)
    });


    this.budgetService.getBudget().subscribe({
      next: (data) => this.totalBudget = data,
      error: (err) => console.error(err)
    });

    this.dataSource.sortingDataAccessor = (item, property) => {
      if (property === 'budgetShare') {
        return (Math.round(item.budgetPlanned / this.totalBudget.value * 100) || 0);
      } else {
        return item[property];
      }
    };
  }

  createForm() {
    const dialogRef = this.dialog.open(RoomDialogComponent, {
      data: {action: "Add"}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.roomService.addRoom(result).subscribe({
          // Table wouldn't refresh on push
          next: (data) => this.dataSource.data = [...this.dataSource.data, data],
          error: (err) => console.error(err)
        });
    });
  }

  editForm(room: any) {
    const dialogRef = this.dialog.open(RoomDialogComponent, {
      data: {...room, action: 'Edit'}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.roomService.updateRoom(result).subscribe({
          next: (data) => {
            this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== room.id);
            this.dataSource.data.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

  removeForm(room: any) {
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.roomService.deleteRoom(room).subscribe({
          next: (data) => this.dataSource.data = this.dataSource.data.filter((r: { id: any; }) => r.id !== room.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

}
