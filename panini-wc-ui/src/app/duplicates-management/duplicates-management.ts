import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef,
} from '@angular/core';

import { CommonModule } from '@angular/common';

import Keycloak from 'keycloak-js';

import {
  DuplicateService,
} from '../services/duplicate.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-duplicates-management',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './duplicates-management.html',
  styleUrl: './duplicates-management.scss',
})
export class DuplicatesManagement
  implements OnInit {

  // ─────────────────────────────────────────
  // INJECTIONS
  // ─────────────────────────────────────────

  private readonly duplicateService =
    inject(DuplicateService);

  private readonly keycloak =
    inject(Keycloak);

  private readonly cdr =
    inject(ChangeDetectorRef);

  // ─────────────────────────────────────────
  // DATA
  // ─────────────────────────────────────────

  duplicates: any[] = [];

  loading = false;

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

    this.loadDuplicates();
  }

  // ─────────────────────────────────────────
  // LOAD DUPLICATES
  // ─────────────────────────────────────────

  loadDuplicates(): void {

    this.loading = true;

    this.duplicateService
      .getDuplicates(
        this.email
      )
      .subscribe({

        next: (response) => {

          this.duplicates =
            response;

          this.loading = false;

          this.cdr.detectChanges();
        },

        error: (err) => {

          console.error(err);

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }

  // ─────────────────────────────────────────
  // PROCESSING
  // ─────────────────────────────────────────

  isProcessing(
    code: string
  ): boolean {

    return this.processing
      .has(code);
  }

  // ─────────────────────────────────────────
  // REDUCE
  // ─────────────────────────────────────────

  reduce(
    duplicate: any
  ): void {

    if (
      this.isProcessing(
        duplicate.code
      )
    ) {

      return;
    }

    this.processing
      .add(duplicate.code);

    this.duplicateService
      .reduceDuplicate({

        email: this.email,

        place: duplicate.code
      })
      .subscribe({

        next: () => {

          if (
            duplicate.number > 2
          ) {

            duplicate.number--;
          } else {

            this.duplicates =
              this.duplicates.filter(
                d =>
                  d.id !==
                  duplicate.id
              );
          }

          this.processing
            .delete(
              duplicate.code
            );

          this.cdr.detectChanges();
        },

        error: (err) => {

          console.error(err);

          this.processing
            .delete(
              duplicate.code
            );

          this.cdr.detectChanges();
        }
      });
  }

  // ─────────────────────────────────────────
  // REMOVE
  // ─────────────────────────────────────────

  remove(
    duplicate: any
  ): void {

    if (
      this.isProcessing(
        duplicate.code
      )
    ) {

      return;
    }

    this.processing
      .add(duplicate.code);

    this.duplicateService
      .deleteDuplicate({

        email: this.email,

        place: duplicate.code
      })
      .subscribe({

        next: () => {

          this.duplicates =
            this.duplicates.filter(
              d =>
                d.id !==
                duplicate.id
            );

          this.processing
            .delete(
              duplicate.code
            );

          this.cdr.detectChanges();
        },

        error: (err) => {

          console.error(err);

          this.processing
            .delete(
              duplicate.code
            );

          this.cdr.detectChanges();
        }
      });
  }
}
