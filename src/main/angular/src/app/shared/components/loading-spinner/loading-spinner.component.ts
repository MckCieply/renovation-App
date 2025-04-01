import { Component } from '@angular/core';
import {LoadingService} from "../../services/loading.service";

@Component({
  selector: 'app-loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrl: './loading-spinner.component.scss'
})
export class LoadingSpinnerComponent {
  isLoading$ = this.loadingService.isLoading$;
  constructor(private loadingService: LoadingService) {}
}
