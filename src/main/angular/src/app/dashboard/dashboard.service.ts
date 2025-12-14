import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private baseUrl = 'http://localhost:8080/api/analytics';

  constructor(private http: HttpClient) {}

  getCostBreakdown() {
    return this.http.get(`${this.baseUrl}/costs`);
  }

  getRoomHealth() {
    return this.http.get<any[]>(`${this.baseUrl}/roomsHealth`);
  }
}
