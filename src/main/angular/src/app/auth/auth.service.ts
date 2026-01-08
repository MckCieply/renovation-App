import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from "@angular/router";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private api = 'http://localhost:8080/api/auth';
  private jwtToken: string | null = null;

  notificationService = inject(NotificationService)

  constructor(private http: HttpClient,
              private router: Router) {
  }

  login(credentials: Object): Observable<any> {
    return this.http.post<any>(this.api + '/login', credentials);
  }

  register(credentials: Object): Observable<any> {
    return this.http.post<any>(this.api + '/register', credentials).pipe(
      tap(() => this.notificationService.showSuccess('Konto zostało utworzone pomyślnie!'))
    );
  }

  roles(): Observable<any> {
    return this.http.get<any>(this.api + '/roles');
  }

  logout() {
    this.clearToken();
    this.router.navigate(['Auth/Login']);
  }

  authSuccess(username: string) {
    this.router.navigate(['/Dashboard']);
    this.setUsername(username);
  }

  setToken(token: string) {
    this.jwtToken = token;
    localStorage.setItem('renovationApp.token', token);
  }

  getToken(): string | null {
    if (!this.jwtToken) {
      this.jwtToken = localStorage.getItem('renovationApp.token');
    }
    return this.jwtToken;
  }

  clearToken() {
    this.jwtToken = null;
    localStorage.removeItem('renovationApp.token');
  }

  isAuthenticated(): boolean {
    return this.getToken() != null;
  }

  setUsername(username: string) {
    localStorage.setItem('username', username);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }


}
