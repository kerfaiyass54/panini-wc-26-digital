import {
  provideKeycloak
} from 'keycloak-angular';

export const provideKeycloakAngular =
  provideKeycloak({

    config: {

      url: 'http://localhost:8080',

      realm: 'panini',

      clientId: 'panini-ui'
    },

    initOptions: {

      onLoad: 'login-required',

      checkLoginIframe: false
    }
  });
