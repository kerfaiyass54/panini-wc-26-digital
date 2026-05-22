import {
  Component,
  HostListener,
  Input,
  inject,
  Signal
} from '@angular/core';

import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import {
  KEYCLOAK_EVENT_SIGNAL,
  KeycloakEventType
} from 'keycloak-angular';

import Keycloak from 'keycloak-js';

export interface NavLink {

  label: string;

  path: string;

  exact?: boolean;
}

@Component({
  selector: 'app-nav-bar',

  standalone: true,

  imports: [
    RouterLink,
    RouterLinkActive
  ],

  templateUrl: './nav-bar.html',

  styleUrl: './nav-bar.scss',
})
export class NavBar {

  @Input()
  title = 'Panini WC';

  @Input()
  links: NavLink[] = [

    {
      label: 'Total Stats',
      path: '/total-stats'
    },

    {
      label: 'Sticker Details',
      path: '/stickers-details'
    },

    {
      label: 'Sticker Stats',
      path: '/stickers-stats'
    },

    {
      label: 'Nations Stats',
      path: '/nations-stats'
    }
  ];

  scrolled = false;

  username = '';

  email = '';

  private readonly keycloakSignal:
    Signal<any> = inject(
    KEYCLOAK_EVENT_SIGNAL
  );

  constructor() {

    const event =
      this.keycloakSignal();

    if (
      event.type ===
      KeycloakEventType.Ready
    ) {

      const keycloak =
        event.args as Keycloak;

      this.username =
        keycloak.tokenParsed?.['preferred_username'] || '';

      this.email =
        keycloak.tokenParsed?.['email'] || '';
    }
  }

  logout(): void {

    const event =
      this.keycloakSignal();

    const keycloak =
      event.args as Keycloak;

    keycloak.logout({
      redirectUri:
      window.location.origin
    });
  }

  @HostListener('window:scroll')
  onScroll(): void {

    this.scrolled =
      window.scrollY > 10;
  }
}
