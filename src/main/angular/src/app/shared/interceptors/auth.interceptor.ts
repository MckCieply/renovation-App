import {inject, Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpInterceptorFn, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {AuthService} from "../../auth/auth.service";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const userToken = authService.getToken();

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
