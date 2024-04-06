import {Component, inject} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  invalidCredentials = false;
  serverError = false;
  loginForm: FormGroup;

  authService = inject(AuthService)

  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }


  onSubmit() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value)
      this.authService.login(this.loginForm.value).subscribe({
        next: response => {
          this.authService.setToken(response.token)
          this.authService.authSuccess(this.loginForm.value.username)
        },
        error: error => {
          if(error.status === 403 || error.status === 401)
            this.invalidCredentials = true;

          if(error.status === 500) {
            this.serverError = true;
            console.error("Internal server error")
          }
        }
      });
    }
  }


}
