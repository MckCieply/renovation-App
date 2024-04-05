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

export const passwordStrengthValidator = (control: AbstractControl) => {
  const password = control.get('password');
  let passwordErrors = {};
  if(password?.value) {
    const passwordValue = password.value;
    if(passwordValue.length < 8)
      passwordErrors = {...passwordErrors, passwordTooShort: true};

    if(!passwordValue.match(/[A-Z]/))
      passwordErrors = {...passwordErrors, passwordNoUppercase: true};

    if(!passwordValue.match(/[a-z]/))
      passwordErrors = {...passwordErrors, passwordNoLowercase: true};

    if(!passwordValue.match(/[0-9]/))
      passwordErrors = {...passwordErrors, passwordNoNumber: true};

    if(Object.keys(passwordErrors).length > 0) {
      password.setErrors(passwordErrors);
      return passwordErrors;
    }
  }
  return null;
}
