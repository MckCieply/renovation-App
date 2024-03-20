import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ContractorsService {

  constructor(private httpClient: HttpClient) { }

  private api = 'http://localhost:8080/api/contractors';

  getAllContractors() {
    return this.httpClient.get(this.api + '/all');
  }

  addContractor(contractor: any) {
    return this.httpClient.post(this.api + '/add', contractor);
  }

  deleteContractor(contractor: any) {
    return this.httpClient.delete(this.api + '/delete/' + contractor.id);
  }

  updateContractor(contractor: any) {
    return this.httpClient.put(this.api + '/update', contractor);
  }
}
