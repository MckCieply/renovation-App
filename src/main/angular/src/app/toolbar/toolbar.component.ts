import {Component, EventEmitter, inject, Output, ViewChild} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {Router} from "@angular/router";
@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent {

  authService = inject(AuthService);
  @Output() sidenavEmit = new EventEmitter<void>();

    constructor(private router: Router) {
    }

    toggleSidenav(){
      this.sidenavEmit.emit()
    }

  logout(){
    this.authService.logout();
    this.router.navigate(['/Login']);
  }
}
