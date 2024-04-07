import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/api/auth';
  private jwtToken: string | null = null;

  constructor(private http: HttpClient,
              private router: Router) {}

  login(credentials: Object): Observable<any> {
    return this.http.post<any>(this.api + '/login', credentials);
  }

  register(credentials:Object): Observable<any>{
    return this.http.post<any>(this.api + '/register', credentials);
  }

  logout(){
    this.clearToken();
    this.router.navigate(['/Login']);
  }

  authSuccess(username: string){
    this.router.navigate(['/Dashboard']);
    this.setUsername(username);
  }

  setToken(token: string) {
    this.jwtToken = token;
    localStorage.setItem('jwtToken', token);
  }

  getToken(): string | null {
    if (!this.jwtToken) {
      this.jwtToken = localStorage.getItem('jwtToken');
    }
    return this.jwtToken;
  }

  clearToken() {
    this.jwtToken = null;
    localStorage.removeItem('jwtToken');
  }

  isAuthenticated(): boolean {
    return this.getToken() != null;
  }

  setUsername(username: string){
    localStorage.setItem('username', username);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }


}
