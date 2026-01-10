import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HttpParamsBuilderService} from "../shared/services/http-params-builder.service";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private api = 'http://localhost:8080/api/rooms';

  paramsBuilder = inject(HttpParamsBuilderService)
  notificationService = inject(NotificationService)

  constructor(private httpClient: HttpClient) {
  }

  getAllRooms() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addRoom(room: any) {
    return this.httpClient.post(this.api + '/add', room).pipe(
      tap(() => this.notificationService.showSuccess('Room added successfully!'))
    );
  }

  deleteRoom(room: any) {
    return this.httpClient.delete(this.api + '/delete/' + room.id).pipe(
      tap(() => this.notificationService.showSuccess('Room deleted successfully!'))
    );
  }

  updateRoom(room: any) {
    return this.httpClient.put(this.api + '/update', room).pipe(
      tap(() => this.notificationService.showSuccess('Room updated successfully!'))
    );
  }

  filterRooms(filter: any) {
    let params = this.paramsBuilder.buildHttpParams(filter);

    return this.httpClient.get<any>(this.api + '/filter', {params});
  }

  getMinimal(){
    return this.httpClient.get<any[]>(this.api + '/minimal');
  }

  canDeleteRoom(roomId: number) {
    return this.httpClient.get<{canDelete: boolean, message: string}>(this.api + '/' + roomId + '/can-delete');
  }
}
