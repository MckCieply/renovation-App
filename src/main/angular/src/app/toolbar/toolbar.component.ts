import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../dialogs/confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrl: './toolbar.component.scss'
})
export class ToolbarComponent implements OnInit {
  loggedUser: any = '';

  authService = inject(AuthService);
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
}
