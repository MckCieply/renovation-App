import {inject} from '@angular/core';
import {HttpInterceptorFn} from "@angular/common/http";
import {catchError, throwError} from "rxjs";
import {AuthService} from "../../auth/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const userToken = authService.getToken();

  // Skip adding the token for login and register requests
  if (req.url.includes('/login') || req.url.includes('/register')) {
    return next(req);
  }

  const modifiedReq = userToken
    ? req.clone({ headers: req.headers.set('Authorization', `Bearer ${userToken}`) })
    : req;

  return next(modifiedReq).pipe(
    catchError(error => {
      if (error.status === 401 || error.status === 403) {
        authService.logout();
      }
      return throwError(() => error);
    })
  );
};
