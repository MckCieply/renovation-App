import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HttpParamsBuilderService} from "../shared/services/http-params-builder.service";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  api = "http://localhost:8080/api/works";

  paramsBuilder = inject(HttpParamsBuilderService)
  notificationService = inject(NotificationService)

  constructor(private httpClient: HttpClient) {
  }

  getAllWorks() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addWork(work: any) {
    return this.httpClient.post(this.api + '/add', work).pipe(
      tap(() => this.notificationService.showSuccess('Praca została dodana pomyślnie!'))
    );
  }

  deleteWork(work: any) {
    return this.httpClient.delete(this.api + '/delete/' + work.id).pipe(
      tap(() => this.notificationService.showSuccess('Praca została usunięta pomyślnie!'))
    );
  }

  updateWork(work: any) {
    return this.httpClient.put(this.api + '/update', work).pipe(
      tap(() => this.notificationService.showSuccess('Praca została zaktualizowana pomyślnie!'))
    );
  }

  getEnumWorkStatus() {
    return this.httpClient.get(this.api + '/get-enum-work-state');
  }

  filterWork(filter: any) {
    let params = this.paramsBuilder.buildHttpParams(filter);
    return this.httpClient.get<any>(this.api + '/filter', { params });
  }
}
