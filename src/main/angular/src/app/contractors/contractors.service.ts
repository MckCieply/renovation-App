import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {NotificationService} from "../shared/services/notification.service";

@Injectable({
  providedIn: 'root'
})
export class ContractorsService {

  notificationService = inject(NotificationService)

  constructor(private httpClient: HttpClient) {
  }

  private api = 'http://localhost:8080/api/contractors';

  getAllContractors() {
    return this.httpClient.get<any[]>(this.api + '/all');
  }

  addContractor(contractor: any) {
    return this.httpClient.post(this.api + '/add', contractor).pipe(
      tap(() => this.notificationService.showSuccess('Wykonawca został dodany pomyślnie!'))
    );
  }

  deleteContractor(contractor: any) {
    return this.httpClient.delete(this.api + '/delete/' + contractor.id).pipe(
      tap(() => this.notificationService.showSuccess('Wykonawca został usunięty pomyślnie!'))
    );
  }

  updateContractor(contractor: any) {
    return this.httpClient.put(this.api + '/update', contractor).pipe(
      tap(() => this.notificationService.showSuccess('Wykonawca został zaktualizowany pomyślnie!'))
    );
  }
}
