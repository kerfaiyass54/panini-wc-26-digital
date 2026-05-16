import { Component, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-stickers-total',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './stickers-total.html',
  styleUrl: './stickers-total.scss',
})
export class StickersTotal {

  // TOTAL STICKERS

  totalStickers = signal(684);

  collected = signal(472);

  // CATEGORIES

  logos = signal(32);

  introPictures = signal(18);

  players = signal(422);

  // PERCENTAGE

  percentage = computed(() =>

    Math.round(
      (this.collected() / this.totalStickers()) * 100
    )
  );
}
