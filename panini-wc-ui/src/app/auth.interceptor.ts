import {
  HttpInterceptorFn
} from '@angular/common/http';

import Keycloak from 'keycloak-js';

declare const window: Window & {
  Keycloak?: Keycloak;
};

export const authInterceptor:
  HttpInterceptorFn = (req, next) => {

  const keycloak =
    window.Keycloak;

  if (
    keycloak &&
    keycloak.token
  ) {

    req = req.clone({

      setHeaders: {

        Authorization:
          `Bearer ${keycloak.token}`
      }
    });
  }

  return next(req);
};
