import { HttpInterceptorFn } from '@angular/common/http';
import {inject} from "@angular/core";
import {LoadingService} from "../services/loading.service";
import {delay, finalize, timeout} from "rxjs";

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);
  loadingService.show();



  //todo: remove delay on prod

  return next(req).pipe(delay(500), finalize(() => {
    loadingService.hide()
  }));
};
