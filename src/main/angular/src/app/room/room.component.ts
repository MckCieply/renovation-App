import {Component, inject, OnInit} from '@angular/core';
import {RoomService} from "./room.service";
import {AddRoomDialogComponent} from "./add-room-dialog/add-room-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {EditRoomDialogComponent} from "./edit-room-dialog/edit-room-dialog.component";
import {BudgetService} from "../budget/budget.service";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit{
  rooms: any;
  totalBudget: any;
  roomService = inject(RoomService)
  budgetService = inject(BudgetService)
  protected readonly Math = Math;
  constructor(public dialog: MatDialog) {}

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });

    this.budgetService.getBudget().subscribe({
      next: (data) => this.totalBudget = data,
      error: (err) => console.error(err)
    });
  }

  createForm(){
    const dialogRef = this.dialog.open(AddRoomDialogComponent, {
      data: {name: "", budgetPlanned: ""}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result)
        this.roomService.addRoom(result).subscribe({
          next: (data) => this.rooms.push(data),
          error: (err) => console.error(err)
      });
    });
  }

  removeForm(room:any){
    const dialogRef = this.dialog.open(RemoveDialogComponent)

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.roomService.deleteRoom(room).subscribe({
          next: (data) => this.rooms = this.rooms.filter((r: { id: any; }) => r.id !== room.id),
          error: (err) => console.error(err)
        });
      }
    });
  }

  editForm(room:any){
    const dialogRef = this.dialog.open(EditRoomDialogComponent, {
      data: {id: room.id, name: room.name, budgetPlanned: room.budgetPlanned}
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.roomService.updateRoom(result).subscribe({
          next: (data) => {
            this.rooms = this.rooms.filter((r: { id: any; }) => r.id !== room.id);
            this.rooms.push(data);
          },
          error: (err) => console.error(err)
        });
      }
    });
  }

}
