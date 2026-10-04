export interface Environment {
  production: boolean;
  name: string;
  keycloak: {
    url: string;
    realm: string;
    clientId: string;
  };
  api: {
    sticker: string;
    team: string;
    tournament: string;
    match: string;
  };
}
