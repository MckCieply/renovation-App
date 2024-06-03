import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth/auth.service";
import {UserService} from "./user.service";
import {ConfirmDialogComponent} from "../dialogs/confirm-dialog/confirm-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {passwordConfirmValidator, passwordStrengthValidator} from "../validators";
;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit{
  loggedUser: any = {};
  isAdmin: boolean = false;
  userForm: FormGroup;
  passwordForm: FormGroup;

  authService = inject(AuthService)
  userService = inject(UserService)

  constructor(private fb: FormBuilder,
              private dialog: MatDialog) {
    this.userForm = this.fb.group({
      username: this.loggedUser.username,
      firstName: [this.loggedUser.firstName, Validators.required],
      lastName: [this.loggedUser.lastName, Validators.required],
      email: [this.loggedUser.email, Validators.required],
    });
    this.passwordForm = this.fb.group({
      username: this.authService.getUsername(),
      oldPassword: ['', Validators.required],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required]
    }, {
      validators: [
        passwordConfirmValidator,
        passwordStrengthValidator
      ]
    }
    );
  }

  ngOnInit() {
    const username = this.authService.getUsername() || '';
    console.log(username)
    this.userService.getUser(username).subscribe({
      next: response => {
        this.loggedUser = response;
        this.userForm.patchValue(response);
        this.isAdmin = this.loggedUser.roles.some((role: any) => role.name === 'ADMIN');
      },
      error: error => console.error(error)
    });
  }

  onSubmitUser(){
    if(this.userForm.valid){
      this.userService.updateUser(this.userForm.value).subscribe({
        next: response => {
          console.log(response)
        },
        error: error => console.error(error)
      });
    }
  }

  onSubmitPassword(){
    const {passwordConfirm, ...formData} = this.passwordForm.value;
    if(this.passwordForm.valid){
      const dialogRef = this.dialog.open(ConfirmDialogComponent, {data: {action: "change password, you will be logged out"}});
      dialogRef.afterClosed().subscribe(result => {
        if(result) {
          this.userService.changePassword(formData).subscribe({
            next: response => this.authService.logout(), // TODO: Success dialog, countdown to logout
            error: error => console.error(error)
          });
        }
      })
    }
  }

  getRolesAsString(): string {
    if (this.loggedUser.roles) {
      return this.loggedUser.roles.map((role: any) => this.capitalizeFirstLetter(role.name)).join(', ');
    }
    return '';
  }

  capitalizeFirstLetter(role: string): string {
    return role.charAt(0).toUpperCase() + role.toLowerCase().slice(1);
  }

}
