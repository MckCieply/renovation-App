import {Component, inject, OnInit} from '@angular/core';
import {RoomService} from "./room.service";

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit{
  rooms: any;
  roomService = inject(RoomService)
  constructor() {}

  ngOnInit() {
    this.roomService.getAllRooms().subscribe({
      next: (data) => this.rooms = data,
      error: (err) => console.error(err)
    });
  }
}
