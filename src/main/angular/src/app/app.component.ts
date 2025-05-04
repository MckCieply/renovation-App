import {Component, inject} from '@angular/core';
import {AuthService} from "./auth/auth.service";
import {SidebarService} from "./shared/services/sidebar.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'angular';

  authService = inject(AuthService);
  sidebarService = inject(SidebarService);

}
