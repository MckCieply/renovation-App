import {Component, inject, OnInit} from '@angular/core';
import {RoomService} from "./room.service";
import {AddDialogComponent} from "./add-dialog/add-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {RemoveDialogComponent} from "../dialogs/remove-dialog/remove-dialog.component";
import {EditDialogComponent} from "./edit-dialog/edit-dialog.component";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit{
  rooms: any;
  roomService = inject(RoomService)
  constructor(public dialog: MatDialog) {}

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });
  }

  createForm(){
    const dialogRef = this.dialog.open(AddDialogComponent, {
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
    const dialogRef = this.dialog.open(EditDialogComponent, {
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
