import {Component, inject, OnInit} from '@angular/core';
import {UserService} from "../user/user.service";
import {AdminService} from "./admin.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent implements OnInit {
  users: any
  tableColumns = ['username', 'firstName', 'lastName', 'roles', 'actions'];

  adminService = inject(AdminService)
  ngOnInit() {
    this.adminService.getAllUsers().subscribe({
      next: response => {
        this.users = response
        console.log(this.users)
      },
      error: error => console.error(error)
    });
  }

  getRolesAsString(user: any): string {
    if (user.roles) {
      return user.roles.map((role: any) => this.capitalizeFirstLetter(role.name)).join(', ');
    }
    return '';
  }

  capitalizeFirstLetter(role: string): string {
    return role.charAt(0).toUpperCase() + role.toLowerCase().slice(1);
  }

}

