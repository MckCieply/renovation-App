import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private api = 'http://localhost:8080/api/user';

  constructor(private httpClient: HttpClient) {
  }

  getAllUsers() {
    return this.httpClient.get(this.api + '/get-all');
  }

  isAdmin(user: any, admin: any) {
    return this.httpClient.put(this.api + '/update-roles', {user, isAdmin: admin});
  }
}
