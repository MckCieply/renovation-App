import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class WorkService {

  constructor() { }

  endpoint = "http://localhost:8080/api/work";
}
