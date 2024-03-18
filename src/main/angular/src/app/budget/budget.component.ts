import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrl: './budget.component.scss'
})
export class BudgetComponent implements OnInit{

  budget: any;
  constructor(private httpClient: HttpClient) {
  }

  private api = "http://localhost:8080/api/budget";
  updateBudget(budget: any){
    return this.httpClient.put(this.api + '/update', budget).subscribe({
      next: (data) => this.budget = data,
      error: (err) => console.error(err)
    })
  }

  ngOnInit() {
    this.httpClient.get(this.api + '/get').subscribe({
      next: (data) => this.budget = data,
      error: (err) => console.error(err)
    });
  }
}
