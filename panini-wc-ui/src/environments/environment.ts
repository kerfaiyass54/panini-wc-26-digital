import { Environment } from './environment.model';

export const environment: Environment = {
  production: false,
  name: 'panini-wc-ui',
  keycloak: {
    url: 'http://localhost:8080',
    realm: 'panini',
    clientId: 'panini-ui',
  },
  api: {
    sticker: 'http://localhost:9090/api',
    team: 'http://localhost:9094/api/teams',
    tournament: 'http://localhost:9095/api/tournaments',
    match: 'http://localhost:9095/api/matches',
  },
};
