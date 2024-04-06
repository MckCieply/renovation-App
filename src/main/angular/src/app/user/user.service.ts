import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private api = 'http://localhost:8080/api/users';
  constructor(private httpClient: HttpClient ) {
  }

  getUser(){
    return this.httpClient.get(this.api + '/single');
  }

  updateUser(user: any){
    return this.httpClient.put(this.api + '/update', user);
  }

  // update password that logs off the user with dialog info
}
