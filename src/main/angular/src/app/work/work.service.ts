import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {HttpParamsBuilderService} from "../shared/services/http-params-builder.service";

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  api = "http://localhost:8080/api/works";

  paramsBuilder = inject(HttpParamsBuilderService)

  constructor(private httpClient: HttpClient) {
  }

  getAllWorks() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addWork(work: any) {
    return this.httpClient.post(this.api + '/add', work);
  }

  deleteWork(work: any) {
    return this.httpClient.delete(this.api + '/delete/' + work.id);
  }

  updateWork(work: any) {
    return this.httpClient.put(this.api + '/update', work);
  }

  getEnumWorkStatus() {
    return this.httpClient.get(this.api + '/get-enum-work-state');
  }

  filterWork(filter: any) {
    let params = this.paramsBuilder.buildHttpParams(filter);
    return this.httpClient.get<any>(this.api + '/filter', { params });
  }
}
