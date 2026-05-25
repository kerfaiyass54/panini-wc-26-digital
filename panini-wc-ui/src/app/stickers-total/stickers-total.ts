import {
  Component,
  OnInit,
  inject, ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';

import Keycloak from 'keycloak-js';

import {
  StatisticsResponse,
  StickerService
} from '../services/sticker.service';

@Component({
  selector: 'app-stickers-total',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './stickers-total.html',
  styleUrl: './stickers-total.scss',
})
export class StickersTotal
  implements OnInit {

  private readonly stickerService =
    inject(StickerService);

  private readonly keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  stats: any = {
    totalOwnedStickers: 0,
    totalFinishedCountries: 0,
    totalLogos: 0,
    totalPlayers: 0
  };

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

    this.stickerService
      .getStatistics(this.email)
      .subscribe({

        next: (response) => {

          this.stats = response;
          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);
        }
      });
  }

  // ─────────────────────────────────────────

}
