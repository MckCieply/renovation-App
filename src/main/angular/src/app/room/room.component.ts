import {Component, inject, OnInit} from '@angular/core';
import {RoomService} from "./room.service";
import {Router} from "@angular/router";
import {RoomFormComponent} from "./room-form/room-form.component";
import {MatDialog} from "@angular/material/dialog";


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit{
  rooms: any;
  roomService = inject(RoomService)
  constructor(private router: Router,
              public dialog: MatDialog) {}

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });
  }

  createForm(){
    const dialogRef = this.dialog.open(RoomFormComponent, {
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
    this.roomService.deleteRoom(room).subscribe({
      next: (data) => this.rooms = this.rooms.filter((r: { id: any; }) => r.id !== room.id),
      error: (err) => console.error(err)
    });
  }
}
