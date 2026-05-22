import {
  Component,
  HostListener,
  Input,
  inject
} from '@angular/core';

import {
  RouterLink,
  RouterLinkActive
} from '@angular/router';

import Keycloak
  from 'keycloak-js';

export interface NavLink {

  label: string;

  path: string;

  exact?: boolean;
}

@Component({
  selector: 'app-nav-bar',
  imports: [
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.scss',
})
export class NavBar {

  @Input() title = 'Panini WC';

  @Input() links: NavLink[] = [

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

  private keycloak =
    inject(Keycloak);

  scrolled = false;

  get username(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'preferred_username'
        ] as string
    ) ?? '';
  }

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  get avatarLetter(): string {

    return this.username
      ?.charAt(0)
      ?.toUpperCase() || 'U';
  }

  logout(): void {

    this.keycloak.logout({

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
