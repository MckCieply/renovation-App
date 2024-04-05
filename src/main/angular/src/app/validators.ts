import {AbstractControl} from "@angular/forms";

export const passwordConfirmValidator = (control: AbstractControl) => {
  const password = control.get('password');
  const passwordConfirm = control.get('passwordConfirm');
  if (password && passwordConfirm && password.value != passwordConfirm.value) {
    passwordConfirm.setErrors({passwordMismatch: true});
    return {passwordMismatch: true};
  }
  return null;
}
