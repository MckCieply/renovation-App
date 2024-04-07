import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private api = 'http://localhost:8080/api/user';
  constructor(private httpClient: HttpClient ) {
  }

  getUser(username: string){
    return this.httpClient.get(this.api + '/get', {params: {username}});
  }

  updateUser(user: any){
    return this.httpClient.put(this.api + '/update', user);
  }

  // update password that logs off the user with dialog info
}
