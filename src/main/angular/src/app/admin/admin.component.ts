import {Component, inject, OnInit} from '@angular/core';
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
    this.getAllUsers();
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

  hasAdminRole(user: any): boolean {
    return user.roles?.some((role: any) => role.name === 'ADMIN') ?? false;
  }

  toggleAdminRole(user: any) {
    const isAdmin = !this.hasAdminRole(user);
    this.adminService.isAdmin(user, isAdmin).subscribe({
      next: () => this.getAllUsers(),
      error: error => console.error(error)
    });
  }

  addAdminPrivileges(user: any) {
    this.adminService.isAdmin(user, true).subscribe({
      next: () => this.getAllUsers(),
      error: error => console.error(error)
    });
  }

  removeAdminPrivileges(user: any) {
    this.adminService.isAdmin(user, false).subscribe({
      next: () => this.getAllUsers(),
      error: error => console.error(error)
    });
  }

  getAllUsers() {
    this.adminService.getAllUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error(err)
    });
  }

}

