import {Component, inject} from '@angular/core';
import { FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {ErrorStateMatcher} from "@angular/material/core";
import {passwordConfirmValidator, passwordStrengthValidator} from "../../validators";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerForm: FormGroup;

  matcher = new ErrorStateMatcher();
  authService = inject(AuthService)

  constructor(private fb: FormBuilder) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required]
    }, {validators: [passwordConfirmValidator, passwordStrengthValidator]});
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const {passwordConfirm, ...formData} = this.registerForm.value;
      this.authService.register(formData).subscribe({
        next: response => {
          this.authService.setToken(response.token)
          this.authService.authSuccess(this.registerForm.value.username)
        },
        error: error => console.error(error)
      });
    }
  }
}
