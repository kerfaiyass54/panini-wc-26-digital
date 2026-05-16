import {
  Component,
  computed,
  inject,
  signal,
  OnInit,
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { StickerService, StickerStatsDTO } from '../services/sticker.service';



@Component({
  selector: 'app-stickers-total',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './stickers-total.html',
  styleUrl: './stickers-total.scss',
})
export class StickersTotal implements OnInit {

  private readonly stickerService =
    inject(StickerService);

  // SIGNALS

  totalStickers = signal(528);

  collected = signal(0);

  logos = signal(0);

  introPictures = signal(0);

  players = signal(0);

  // PERCENTAGE

  percentage = computed(() => {

    if (this.totalStickers() === 0) {

      return 0;
    }

    return Math.round(
      (this.collected() / this.totalStickers()) * 100
    );
  });

  // INIT

  ngOnInit(): void {

    this.loadStats();
  }

  // LOAD STATS

  loadStats(): void {

    this.stickerService
      .getStats()
      .subscribe({

        next: (stats: StickerStatsDTO) => {

          this.totalStickers.set(528);

          // collected = all stickers for now

          this.collected.set(stats.total);

          this.logos.set(stats.logos);

          this.introPictures.set(stats.intros);

          this.players.set(stats.players);
        },

        error: (err) => {

          console.error(
            'Error loading stats',
            err
          );
        },
      });
  }
}
