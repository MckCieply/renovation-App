import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  api = "http://localhost:8080/api/works";

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
}
