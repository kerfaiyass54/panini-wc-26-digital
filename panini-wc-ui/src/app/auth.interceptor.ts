import {
  HttpEvent,
  HttpInterceptorFn
} from '@angular/common/http';

import { inject }
  from '@angular/core';

import {
  KeycloakService
} from 'keycloak-angular';
import { Observable } from 'rxjs';

export const authInterceptor:
  (req: any, next: any) => Promise<Observable<HttpEvent<unknown>>> = async (
  req,
  next
) => {

  const keycloak =
    inject(KeycloakService);

  const token =
    await keycloak.getToken();

  const cloned =
    req.clone({

      setHeaders: {

        Authorization:
          `Bearer ${token}`
      }
    });

  return next(cloned);
};
