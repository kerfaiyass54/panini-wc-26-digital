import {
  Component,
  OnInit,
  OnDestroy,
  inject,
  ChangeDetectorRef,
  AfterViewInit,
  ElementRef,
  ViewChild,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  ReactiveFormsModule,
  FormControl,
} from '@angular/forms';

import { Subscription } from 'rxjs';

import Keycloak from 'keycloak-js';

import {
  StickerService,
  Owning,
  Duplicate,
  PageResponse,
} from '../services/sticker.service';

import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-stickers-details',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './stickers-details.html',
  styleUrl: './stickers-details.scss',
})
export class StickersDetails
  implements
    OnInit,
    OnDestroy,
    AfterViewInit {

  private readonly stickerService =
    inject(StickerService);

  private keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  // ─────────────────────────────────────────
  // VIEWCHILD
  // ─────────────────────────────────────────

  @ViewChild('ownedSection')
  ownedSection!: ElementRef;

  @ViewChild('duplicateSection')
  duplicateSection!: ElementRef;

  // ─────────────────────────────────────────
  // SEARCH
  // ─────────────────────────────────────────

  searchControl =
    new FormControl('');

  private searchSub?: Subscription;

  // ─────────────────────────────────────────
  // OWNED SEARCH
  // ─────────────────────────────────────────

  ownedSearch =
    new FormControl('');

  duplicateSearch =
    new FormControl('');

  // ─────────────────────────────────────────
  // TABLE DATA
  // ─────────────────────────────────────────

  ownedRows: Owning[] = [];

  filteredOwned: Owning[] = [];

  duplicateRows: Duplicate[] = [];

  filteredDuplicates: Duplicate[] = [];

  // ─────────────────────────────────────────
  // PAGINATION
  // ─────────────────────────────────────────

  ownedPage = 0;

  duplicatePage = 0;

  pageSize = 5;

  ownedTotalPages = 0;

  duplicateTotalPages = 0;

  // ─────────────────────────────────────────
  // ANIMATION
  // ─────────────────────────────────────────

  showOwned = false;

  showDuplicates = false;

  // ─────────────────────────────────────────
  // CAROUSEL
  // ─────────────────────────────────────────

  private autoScrollInterval: any;

  isPaused = false;

  nationalityCounts:
    Record<string, number> = {};

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
    'cabo verde',
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
    'ir iran',
    'iraq',
    'côte d’ivoire',
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
    'korea republic',
    'spain',
    'sweden',
    'switzerland',
    'tunisia',
    'türkiye',
    'usa',
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

    this.loadOwnings();

    this.loadDuplicates();

    setTimeout(() => {

      this.startAutoScroll();

    }, 500);

    this.nations.forEach(nation => {

      this.stickerService
        .countByNationality(
          nation,
          this.email
        )
        .subscribe(count => {

          this.nationalityCounts[nation] =
            count;

          this.cdr.detectChanges();
        });
    });

    // SEARCH OWNED

    this.ownedSearch
      .valueChanges
      .subscribe(value => {

        const query =
          (value || '')
            .toLowerCase();

        this.filteredOwned =
          this.ownedRows.filter(row =>
            row.place
              .toLowerCase()
              .includes(query)
          );
      });

    // SEARCH DUPLICATES

    this.duplicateSearch
      .valueChanges
      .subscribe(value => {

        const query =
          (value || '')
            .toLowerCase();

        this.filteredDuplicates =
          this.duplicateRows.filter(row =>
            row.place
              .toLowerCase()
              .includes(query)
          );
      });
  }

  // ─────────────────────────────────────────

  ngAfterViewInit(): void {

    const observer =
      new IntersectionObserver(entries => {

        entries.forEach(entry => {

          if (
            entry.target ===
            this.ownedSection.nativeElement
          ) {

            this.showOwned =
              entry.isIntersecting;
          }

          if (
            entry.target ===
            this.duplicateSection.nativeElement
          ) {

            this.showDuplicates =
              entry.isIntersecting;
          }

          this.cdr.detectChanges();
        });

      }, {
        threshold: 0.2
      });

    observer.observe(
      this.ownedSection.nativeElement
    );

    observer.observe(
      this.duplicateSection.nativeElement
    );
  }

  // ─────────────────────────────────────────

  loadOwnings(): void {

    this.stickerService
      .getOwnings(
        this.email,
        this.ownedPage,
        this.pageSize
      )
      .subscribe({

        next: (
          response:
          PageResponse<Owning>
        ) => {

          this.ownedRows =
            response.content;

          this.filteredOwned =
            response.content;

          this.ownedTotalPages =
            response.totalPages;

          this.cdr.detectChanges();
        }
      });
  }

  // ─────────────────────────────────────────

  loadDuplicates(): void {

    this.stickerService
      .getDuplicates(
        this.email,
        this.duplicatePage,
        this.pageSize
      )
      .subscribe({

        next: (
          response:
          PageResponse<Duplicate>
        ) => {

          this.duplicateRows =
            response.content;

          this.filteredDuplicates =
            response.content;

          this.duplicateTotalPages =
            response.totalPages;

          this.cdr.detectChanges();
        }
      });
  }

  // ─────────────────────────────────────────

  nextOwned(): void {

    if (
      this.ownedPage <
      this.ownedTotalPages - 1
    ) {

      this.ownedPage++;

      this.loadOwnings();
    }
  }

  prevOwned(): void {

    if (this.ownedPage > 0) {

      this.ownedPage--;

      this.loadOwnings();
    }
  }

  // ─────────────────────────────────────────

  nextDuplicates(): void {

    if (
      this.duplicatePage <
      this.duplicateTotalPages - 1
    ) {

      this.duplicatePage++;

      this.loadDuplicates();
    }
  }

  prevDuplicates(): void {

    if (this.duplicatePage > 0) {

      this.duplicatePage--;

      this.loadDuplicates();
    }
  }

  // ─────────────────────────────────────────

  truncate(val: number): number {

    return Math.trunc(val);
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

          if (query.length > 0) {

            this.stopAutoScroll();

          } else {

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
