import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import {
  ActivatedRoute,
} from '@angular/router';

import Keycloak from 'keycloak-js';

import {
  StickerService,
  AddStickerRequest,
  DuplicateRequest,
} from '../services/sticker.service';

@Component({
  selector: 'app-nation-stickers',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './nation-stickers.html',
  styleUrl: './nation-stickers.scss',
})
export class NationStickers
  implements OnInit {

  // ─────────────────────────────────────────
  // INJECTIONS
  // ─────────────────────────────────────────

  private readonly route =
    inject(ActivatedRoute);

  private readonly stickerService =
    inject(StickerService);

  private readonly keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  // ─────────────────────────────────────────
  // DATA
  // ─────────────────────────────────────────

  nationality = '';

  stickers: any[] = [];

  ownedStickers =
    new Set<string>();

  processing =
    new Set<string>();

  // ─────────────────────────────────────────
  // EMAIL
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
  // INIT
  // ─────────────────────────────────────────

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      this.nationality =
        params.get('nationality') ?? '';

      this.loadStickers();
    });
  }

  // ─────────────────────────────────────────
  // LOAD STICKERS
  // ─────────────────────────────────────────

  private loadStickers(): void {

    this.stickerService
      .getByNationality(
        this.nationality
      )
      .subscribe({

        next: (response) => {

          this.stickers = response;

          this.cdr.detectChanges();

          this.checkOwnedStickers();
        },

        error: (err) => {

          console.error(err);
        }
      });
  }

  // nation-stickers.ts

  sendAddedObjectWithDate(
    place: string,
    name: string
  ): void {

    const item = {

      code: place,

      name: name,

      createdAt:
        new Date().toISOString()
    };

    this.stickerService
      .addDate(item)
      .subscribe({

        next: (response1) => {

          console.log(response1);
        },

        error: (err) => {

          console.error(err);
        }
      });
  }

  // ─────────────────────────────────────────
  // CHECK OWNED
  // ─────────────────────────────────────────

  private checkOwnedStickers(): void {

    this.stickers.forEach(sticker => {

      this.stickerService
        .hasSticker({

          email: this.email,

          place: sticker.place
        })
        .subscribe({

          next: (owned) => {

            if (owned) {

              this.ownedStickers
                .add(sticker.place);
            }

            this.cdr.detectChanges();
          },

          error: () => {

            this.cdr.detectChanges();
          }
        });
    });
  }

  // ─────────────────────────────────────────
  // HAS STICKER
  // ─────────────────────────────────────────

  hasSticker(
    place: string
  ): boolean {

    return this.ownedStickers
      .has(place);
  }

  // ─────────────────────────────────────────
  // IS PROCESSING
  // ─────────────────────────────────────────

  isProcessing(
    place: string
  ): boolean {

    return this.processing
      .has(place);
  }

  // ─────────────────────────────────────────
  // ADD STICKER
  // ─────────────────────────────────────────

  addSticker(
    place: string
  ): void {

    if (this.isProcessing(place)) {

      return;
    }

    this.processing.add(place);

    const request:
      AddStickerRequest = {

      email: this.email,

      code: place,
    };

    this.stickerService
      .addSticker(request)
      .subscribe({

        next: () => {

          const result = this.stickers.find(
            x => x.place === place
          );
          this.sendAddedObjectWithDate(place,result.name);

          this.ownedStickers
            .add(place);

          this.processing
            .delete(place);

          this.cdr.detectChanges();
        },

        error: (err) => {

          console.error(err);

          this.processing
            .delete(place);

          this.cdr.detectChanges();
        }
      });
  }

  // ─────────────────────────────────────────
  // DUPLICATE STICKER
  // ─────────────────────────────────────────

  duplicateSticker(
    place: string
  ): void {

    if (this.isProcessing(place)) {

      return;
    }

    this.processing.add(place);

    const request:
      DuplicateRequest = {

      email: this.email,

      place,
    };

    this.stickerService
      .duplicateSticker(request)
      .subscribe({

        next: () => {

          this.processing
            .delete(place);
          const result = this.stickers.find(
            x => x.place === place
          );
          this.sendAddedObjectWithDate(place,result.name);


          this.cdr.detectChanges();
        },

        error: () => {

          this.stickerService
            .increaseDuplicate(request)
            .subscribe({

              next: () => {

                this.processing
                  .delete(place);

                this.cdr.detectChanges();
              },

              error: (err) => {

                console.error(err);

                this.processing
                  .delete(place);

                this.cdr.detectChanges();
              }
            });
        }
      });
  }

  // ─────────────────────────────────────────
  // BACK
  // ─────────────────────────────────────────

  goBack(): void {

    window.history.back();
  }
}
