import { provideKeycloak } from 'keycloak-angular';
import { environment } from '../../environments/environment';

export const provideKeycloakAngular = provideKeycloak({
  config: environment.keycloak,
  initOptions: {
    onLoad: 'login-required',
    checkLoginIframe: false,
  },
});
