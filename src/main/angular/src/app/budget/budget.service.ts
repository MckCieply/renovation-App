import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Budget} from "../shared/models/budget.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  private api = "http://localhost:8080/api/budget";

  constructor(private httpClient: HttpClient) {
  }

  updateBudget(budget: any) {
    return this.httpClient.put(this.api + '/update', budget)
  }

  getBudget(): Observable<Budget> {
    return this.httpClient.get<Budget>(this.api + '/budget');
  }
}
