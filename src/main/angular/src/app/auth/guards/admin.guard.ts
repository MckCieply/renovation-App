import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from "../auth.service";
import {inject} from "@angular/core";
import {map} from "rxjs";

export const adminGuard: CanActivateFn = (route, state) => {


  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated())
    return authService.roles().pipe(
      map(roles => {
        if (roles.includes('ROLE_ADMIN'))
          return true;
        else
          return router.createUrlTree(['/Dashboard']);

      })
    )

  else
    return router.createUrlTree(['/Login']);
};
