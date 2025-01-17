import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../dialogs/confirm-dialog/confirm-dialog.component";
import {ThemeService} from "../shared/services/theme.service";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
  darkTheme: boolean = false;
  iconTheme = 'dark_mode'
  loggedUser: any = '';

  authService = inject(AuthService);
  themeService = inject(ThemeService)
  @Output() sidenavEmit = new EventEmitter<void>();

  constructor(public dialog: MatDialog) {
  }

  ngOnInit() {
    this.loggedUser = this.authService.getUsername();
  }

  toggleSidenav() {
    this.sidenavEmit.emit()
  }

  logout() {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {data: {action: "logout"}});
    dialogRef.afterClosed().subscribe(result => {
      if (result)
        this.authService.logout()
    })
  }

  // List of themes: deeppurple-amber, indigo-pink, pink-bluegrey, purple-green
  toggleTheme() {
    this.themeService.setStyle('theme', this.darkTheme ? 'indigo-pink.css' : 'purple-green.css');
    this.iconTheme = this.darkTheme ? 'dark_mode' : 'light_mode';
    this.darkTheme = !this.darkTheme;
  }
}
