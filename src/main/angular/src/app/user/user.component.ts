import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth/auth.service";
import {UserService} from "./user.service";
;

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrl: './user.component.scss'
})
export class UserComponent implements OnInit{
  loggedUser: any = {};
  userForm: FormGroup;
  passwordForm: FormGroup;

  authService = inject(AuthService)
  userService = inject(UserService)

  constructor(private fb: FormBuilder) {
    this.userForm = this.fb.group({
      username: this.loggedUser.username,
      firstName: [this.loggedUser.firstName, Validators.required],
      lastName: [this.loggedUser.lastName, Validators.required],
      email: [this.loggedUser.email, Validators.required],
    });
    this.passwordForm = this.fb.group({
      username: this.authService.getUsername(),
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      newPasswordConfirm: ['', Validators.required]
    });
  }

  ngOnInit() {
    const username = this.authService.getUsername() || '';
    console.log(username)
    this.userService.getUser(username).subscribe({
      next: response => {
        this.loggedUser = response;
        this.userForm.patchValue(response);
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
    const {newPasswordConfirm, ...formData} = this.passwordForm.value;
    if(this.passwordForm.valid){
      this.userService.changePassword(formData).subscribe({
        next: response => {
          console.log(response)
          // TODO: Success dialog, countdown to logout
          this.authService.logout();
        },
        error: error => console.error(error)
      });
    }
  }
}
