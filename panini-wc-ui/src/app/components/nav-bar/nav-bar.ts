import { Component, HostListener, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

export interface NavLink {
  label: string;
  path:  string;
  exact?: boolean;
}

@Component({
  selector: 'app-nav-bar',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './nav-bar.html',
  styleUrl: './nav-bar.scss',
})
export class NavBar {

  @Input() title = 'Panini WC';

  @Input() links: NavLink[] = [
    { label: 'Total Stats',      path: '/total-stats',    exact: false },
    { label: 'Sticker Details',  path: '/stickers-details', exact: false },
    { label: 'Sticker stats',  path: '/stickers-stats', exact: false },
    { label: 'Nations stats',  path: '/nations-stats', exact: false }
  ];

  scrolled = false;

  @HostListener('window:scroll')
  onScroll(): void {
    this.scrolled = window.scrollY > 10;
  }
}
