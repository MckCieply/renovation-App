import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Budget} from "../shared/models/budget.model";
import {Observable} from "rxjs";
import {BudgetValidation} from "../shared/models/budget-validation.model";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class BudgetService {

  private api = "http://localhost:8080/api/budget";

  notificationService = inject(NotificationService)

  constructor(private httpClient: HttpClient) {
  }

  updateBudget(budget: any): Observable<BudgetValidation> {
    return this.httpClient.put<BudgetValidation>(this.api + '/update', budget).pipe(
      tap(() => this.notificationService.showSuccess('Budżet został zaktualizowany pomyślnie!'))
    );
  }

  getBudget(): Observable<Budget> {
    return this.httpClient.get<Budget>(this.api + '/budget');
  }

  validateBudget(): Observable<BudgetValidation> {
    return this.httpClient.get<BudgetValidation>(this.api + '/validate');
  }
}
