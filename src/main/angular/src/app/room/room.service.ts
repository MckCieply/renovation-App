import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HttpParamsBuilderService} from "../shared/services/http-params-builder.service";

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private api = 'http://localhost:8080/api/rooms';

  paramsBuilder = inject(HttpParamsBuilderService)

  constructor(private httpClient: HttpClient) {
  }

  getAllRooms() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addRoom(room: any) {
    return this.httpClient.post(this.api + '/add', room);
  }

  deleteRoom(room: any) {
    return this.httpClient.delete(this.api + '/delete/' + room.id);
  }

  updateRoom(room: any) {
    return this.httpClient.put(this.api + '/update', room);
  }

  filterRooms(filter: any) {
    let params = this.paramsBuilder.buildHttpParams(filter);

    return this.httpClient.get<any>(this.api + '/filter', {params});
  }

  getMinimal(){
    return this.httpClient.get<any[]>(this.api + '/minimal');
  }
}
