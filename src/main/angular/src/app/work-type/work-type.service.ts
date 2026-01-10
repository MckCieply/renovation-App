import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class WorkTypeService {

  private api = 'http://localhost:8080/api/work-types';

  notificationService = inject(NotificationService)

  constructor(private httpClient: HttpClient) {
  }

  getAllTypes() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addType(type: any) {
    return this.httpClient.post(this.api + '/add', type).pipe(
      tap(() => this.notificationService.showSuccess('Work type added successfully!'))
    );
  }

  deleteType(type: any) {
    return this.httpClient.delete(this.api + '/delete/' + type.id).pipe(
      tap(() => this.notificationService.showSuccess('Work type deleted successfully!'))
    );
  }

  updateType(type: any) {
    return this.httpClient.put(this.api + '/update', type).pipe(
      tap(() => this.notificationService.showSuccess('Work type updated successfully!'))
    );
  }

  getMinimal(){
    return this.httpClient.get<any[]>(this.api + '/minimal');
  }
}
