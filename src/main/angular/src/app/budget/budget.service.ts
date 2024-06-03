import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

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

  getBudget() {
    return this.httpClient.get(this.api + '/get');
  }
}
