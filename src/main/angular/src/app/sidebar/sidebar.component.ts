import {Component, inject} from '@angular/core';
import {SidebarService} from "../shared/services/sidebar.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {

  sidebarService = inject(SidebarService)

  constructor() {
  }

}
