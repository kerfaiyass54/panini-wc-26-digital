import {
  Component,
  OnInit,
  inject, ChangeDetectorRef
} from '@angular/core';

import {
  ActivatedRoute
} from '@angular/router';

import {
  CommonModule
} from '@angular/common';
import Keycloak from 'keycloak-js';
import { StickerService } from '../services/sticker.service';





@Component({
  selector: 'app-duplicates-compare',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './duplicates-compare.html',
  styleUrl: './duplicates-compare.scss',
})
export class DuplicatesCompare implements OnInit {

  private stickerService = inject(StickerService);
  private route = inject(ActivatedRoute);
  private keycloak = inject(Keycloak);
  private readonly cdr =
    inject(ChangeDetectorRef);

  myMissingForThem: any[] = [];
  theirMissingForMe: any[] = [];

  loading = true;

  get email(): string {
    return (
      this.keycloak
        .tokenParsed?.['email'] as string
    ) ?? '';
  }

  ngOnInit(): void {

    const otherEmail =
      this.route.snapshot.paramMap.get('email') ?? '';

    if (!otherEmail) {
      this.loading = false;
      return;
    }

    this.loadComparison(otherEmail);
  }

  private loadComparison(otherEmail: string): void {

    this.loading = true;

    this.stickerService
      .getNotHaveDuplicates(
        this.email,
        otherEmail
      )
      .subscribe({
        next: (data) => {
          this.myMissingForThem = data;
          this.cdr.detectChanges();

        }
      });

    this.stickerService
      .getNotHaveDuplicates(
        otherEmail,
        this.email
      )
      .subscribe({
        next: (data) => {
          this.theirMissingForMe = data;
          this.loading = false;
          this.cdr.detectChanges();

        },
        error: () => {
          this.loading = false;
        }
      });
  }
}
