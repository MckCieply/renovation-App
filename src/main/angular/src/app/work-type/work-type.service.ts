import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WorkTypeService {

  private api = 'http://localhost:8080/api/work-types';
  constructor(private httpClient: HttpClient) { }

  getAllTypes() {
    return this.httpClient.get(this.api + '/all');
  }

  addType(type: any) {
    return this.httpClient.post(this.api + '/add', type);
  }

  deleteType(type: any) {
    return this.httpClient.delete(this.api + '/delete/' + type.id);
  }

  updateType(type: any) {
    return this.httpClient.put(this.api + '/update', type);
  }
}
