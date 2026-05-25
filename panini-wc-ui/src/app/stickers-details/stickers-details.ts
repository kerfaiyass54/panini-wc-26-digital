import {
  Component,
  OnInit,
  OnDestroy,
  inject,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  ReactiveFormsModule,
  FormControl,
} from '@angular/forms';

import { Subscription } from 'rxjs';

import Keycloak from 'keycloak-js';
import { StickerService } from '../services/sticker.service';

@Component({
  selector: 'app-stickers-details',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './stickers-details.html',
  styleUrl: './stickers-details.scss',
})
export class StickersDetails
  implements OnInit, OnDestroy {

  private readonly stickerService =
    inject(StickerService);

  private keycloak =
    inject(Keycloak);

  // ─────────────────────────────────────────
  // SEARCH
  // ─────────────────────────────────────────

  searchControl =
    new FormControl('');

  private searchSub?: Subscription;

  // ─────────────────────────────────────────
  // CAROUSEL
  // ─────────────────────────────────────────

  private autoScrollInterval: any;

  isPaused = false;

  // ─────────────────────────────────────────
  // NATIONS
  // ─────────────────────────────────────────

  nations: string[] = [
    'algeria',
    'argentina',
    'australia',
    'austria',
    'belgium',
    'bosnia and herzegovina',
    'brazil',
    'canada',
    'cape verde',
    'colombia',
    'congo dr',
    'croatia',
    'curaçao',
    'czechia',
    'ecuador',
    'egypt',
    'england',
    'france',
    'germany',
    'ghana',
    'haiti',
    'iran',
    'iraq',
    'ivory coast',
    'japan',
    'jordan',
    'mexico',
    'morocco',
    'netherlands',
    'new zealand',
    'norway',
    'panama',
    'paraguay',
    'portugal',
    'qatar',
    'saudi arabia',
    'scotland',
    'senegal',
    'south africa',
    'spain',
    'sweden',
    'switzerland',
    'tunisia',
    'turkey',
    'united states',
    'uruguay',
    'uzbekistan'
  ];

  filteredNations: string[] = [];

  // ─────────────────────────────────────────

  get email(): string {

    return (
      this.keycloak
        .tokenParsed?.[
        'email'
        ] as string
    ) ?? '';
  }

  // ─────────────────────────────────────────

  ngOnInit(): void {

    this.filteredNations =
      [...this.nations];

    this.handleSearch();

    setTimeout(() => {

      this.startAutoScroll();

    }, 500);
  }

  // ─────────────────────────────────────────

  ngOnDestroy(): void {

    if (this.autoScrollInterval) {

      clearInterval(this.autoScrollInterval);
    }

    this.searchSub?.unsubscribe();
  }

  // ─────────────────────────────────────────

  private handleSearch(): void {

    this.searchSub =
      this.searchControl.valueChanges
        .subscribe(value => {

          const query =
            (value ?? '')
              .toLowerCase()
              .trim();

          // STOP animation while typing
          if (query.length > 0) {

            this.stopAutoScroll();

          } else {

            // START animation again
            this.startAutoScroll();
          }

          this.filteredNations =
            this.nations.filter(nation =>
              nation.includes(query)
            );
        });
  }

  // ─────────────────────────────────────────

  startAutoScroll(): void {

    if (this.autoScrollInterval) {

      clearInterval(this.autoScrollInterval);
    }

    const carousel =
      document.querySelector(
        '.carousel'
      ) as HTMLElement;

    if (!carousel) return;

    this.autoScrollInterval =
      setInterval(() => {

        if (this.isPaused) return;

        carousel.scrollLeft += 1;

        // infinite loop
        if (
          carousel.scrollLeft >=
          carousel.scrollWidth -
          carousel.clientWidth
        ) {

          carousel.scrollLeft = 0;
        }

      }, 18);
  }

  // ─────────────────────────────────────────

  stopAutoScroll(): void {

    if (this.autoScrollInterval) {

      clearInterval(this.autoScrollInterval);
    }
  }

  // ─────────────────────────────────────────

  pauseCarousel(): void {

    this.isPaused = true;
  }

  // ─────────────────────────────────────────

  resumeCarousel(): void {

    const query =
      (this.searchControl.value ?? '')
        .trim();

    // do not resume if user searching
    if (query.length > 0) {

      return;
    }

    this.isPaused = false;
  }

  // ─────────────────────────────────────────

  trackByNation(
    index: number,
    nation: string
  ): string {

    return nation;
  }
}
